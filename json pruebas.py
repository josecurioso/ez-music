import json
import urllib

class MyOpener(urllib.FancyURLopener):
    version = "User-Agent=Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.2.3) Gecko/20100401 Firefox/3.6.3 ( .NET CLR 3.5.30729)"

def is_obj( data ):
    return type(data) == dict

def download(link, filename):
    #Creando la ruta para guardar
    local_tmp_file = filename
    print local_tmp_file

    # Obteniendo archivo
    my_urlopener = MyOpener()
    print link
    my_urlopener.addheader('Referer', link)
    postparams = None
    response = my_urlopener.open(link, postparams)

    # Guardando archivo
    local_file_handle = open(local_tmp_file, "w+")
    local_file_handle.write(response.read())
    local_file_handle.close()



page = urllib.urlopen('http://www.youtubeinmp3.com/fetch/?format=json&video=https://www.youtube.com/watch?v=BOifp0ulmUc')

data = page.read()

decoded = json.loads(data)

print is_obj(decoded)

print decoded['title']
print decoded['link']

download(decoded['link'], decoded['title']+'.mp3')
