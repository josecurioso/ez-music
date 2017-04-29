#!/usr/bin/env python
# -*- coding: utf-8 -*-
import os
import threading
import urllib
import json
from oauth2client.contrib import gce
import time

#import socket
#socket.setdefaulttimeout(30)

#import pprint
#pp = pprint.PrettyPrinter(depth=6)

developerKey = "AIzaSyB-0w6c7lz4DNWcS4blcHFrb3xl_AkGsvk"  #YouTube developer key for accesing the API


def getLinks(playListLink):
    videoLinks = []
    pageToken = ""
    if playListLink[4] == "s":
        playlistId = playListLink.strip('https://www.youtube.com/playlist?list=')
    elif playListLink[4] == ":":
        playlistId = playListLink.strip('http://www.youtube.com/playlist?list=')
    URL = "https://www.googleapis.com/youtube/v3/playlistItems?key=" + developerKey + "&part=snippet&playlistId=" + playlistId + "&pageToken=" + pageToken
    data = urllib.urlopen(URL).read()
    data = json.loads(data)
    VID_NUM = data['pageInfo']['totalResults']
    counter = 0
    while counter < VID_NUM:
        URL = "https://www.googleapis.com/youtube/v3/playlistItems?key=" + developerKey + "&part=snippet&playlistId=" + playlistId + "&pageToken=" + pageToken
        page = urllib.urlopen(URL).read()
        data = json.loads(page)
        for item in data['items']:
            videoLinks.append("https://www.youtube.com/watch?v=" + item['snippet']['resourceId']['videoId'])
        try:
            pageToken = data['nextPageToken']
        except:
            pass
        counter+=5
    return videoLinks


#Saves a list of text in a file making each position of the list a line
def saveList(list):
    f = open("results.txt", "w")
    for i in list:
        f.write(i + "\n")
    f.close()


def checkSize(yt_url, filename):
    try:
        statinfo = os.stat(filename)  #Gets stats info on the file
        filesize = statinfo.st_size   #Extracts the size from the info
        print filesize                #Prints filesize
        return filesize > 1048000     #Returns true if the size is acceptable
    except:
        return False


def ensure_dir(file_path):
    directory = os.path.dirname(file_path)
    if not os.path.exists(directory):
        os.makedirs(directory)


def download(yt_url, filename):
    #Get all needed info
    url = "http://www.youtubeinmp3.com/fetch/?format=JSON&video=" + yt_url
    try:
        page = urllib.urlopen(url).read()    #Retrieves the indicated page from youtubeinmp3.com
        data = json.loads(page)              #Reads the json data from such page
        filename += data['title'] + ".mp3"   #Assembles the name of the file
        link = data['link']                  #Gets the download link
    except:
        debug(yt_url, filename, 3)
    #Download it or fail
    tries=0
    print filename
    while not checkSize(yt_url, filename):
        try:
            urllib.urlretrieve(link, filename)   #Tries to retrieve the file by url and store it as the filename given
        except:
            debug(yt_url, filename, 3)
        tries += 1
        print tries
        time.sleep(1)
    #Give information about the download process once ended
    if tries==3 and not checkSize(yt_url, filename):
        debug(yt_url, filename, 2)
    else:
        debug(yt_url, filename, 1)


def debug(yt_url, filename, state):
    print
    print
    print ("#################################################################################################")

    if state == 1:
        print ("INFO:   Download " + yt_url + " on " + filename + "\n")
    elif state == 2:
        print ("ERROR:  Download " + yt_url + " on " + filename + " DID NOT MET SIZE\n")
    elif state == 3:
        print ("ERROR:  Download " + yt_url + " on " + filename + " LINK NOT FOUND\n")

    print ("#################################################################################################")
    print
    print


def ytPlaylistDownload(playlistLink, filename):
    videoLinks = getLinks(playlistLink)                               #Get the list of youtube video links
    threads = []
    for link in videoLinks:
        t = threading.Thread(target=download, args=(link,filename,))  #Create thread with the download function
        threads.append(t)                                             #Store thread in the list of threads
        t.daemon = True                                               #Set it as daemon
        t.start()                                                     #Start the thread
    for t in threads:
        t.join()                                                      #Make the main thread wait for all other threads


def ytVideoDownload(videoLink, filename):
    download(videoLink, filename)


def main(link, path):
    ensure_dir(path)   #Make sure the directory exists
    if "www.youtube.com/playlist" in link:    #Check what type of link it is and act upon
        ytPlaylistDownload(link, path)
    elif "www.youtube.com/watch?v" in link:
        ytVideoDownload(link, path)
