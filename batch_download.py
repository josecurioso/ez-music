import json
import urllib

local_download_directory = ""

def download(link, filename):
    urllib.urlretrieve (link, local_directory + filename + ".mp3")

def get_info(url):
    page = urllib.urlopen(url)
    decoded_dict = json.loads(page.read())
    return decoded_dict


url = "http://www.youtubeinmp3.com/fetch/?format=json&video=https://www.youtube.com/watch?v=BOifp0ulmUc"
download(get_info(url)['link'], get_info(url)['title'])
