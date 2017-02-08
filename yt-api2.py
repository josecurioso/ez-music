import urllib2
import json
from oauth2client.contrib import gce
import pprint
pp = pprint.PrettyPrinter(depth=6)
handle = "pewdiepie"
"""
data = urllib2.urlopen("https://www.googleapis.com/youtube/v3/channels?key=AIzaSyB-0w6c7lz4DNWcS4blcHFrb3xl_AkGsvk&part=statistics&forUsername=%s" % handle).read()
data = json.loads(data)

pp.pprint(data['items'])
"""
data = urllib2.urlopen("https://www.googleapis.com/youtube/v3/playlistItems?key=AIzaSyB-0w6c7lz4DNWcS4blcHFrb3xl_AkGsvk&part=snippet&playlistId=PL9fZWvwimefvgG334_-8hDrTzN8EY2j0N").read()
data = json.loads(data)

pp.pprint(data)
