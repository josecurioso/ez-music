import json
import urllib


page = urllib.urlopen('http://www.youtubeinmp3.com/fetch/?format=json&video=https://www.youtube.com/watch?v=BOifp0ulmUc')

data = page.read()


data_string = json.dumps(data)
print 'ENCODED:', data_string

decoded = json.loads(data_string)
print 'DECODED:', decoded
