#!/usr/bin/python
# -*- coding: utf-8 -*-

import sys
from PyQt4 import QtGui
import design
import time
import working as ytdservice


class App(QtGui.QMainWindow, design.Ui_MainWindow):
    def __init__(self):
        # Explaining super is out of the scope of this article
        # So please google it if you're not familar with it
        # super() allows us to access variables, methods etc in the design.py file
        super(self.__class__, self).__init__()
        self.setupUi(self)  # This is defined in design.py file automatically
        # It sets up layout and widgets that are defined
        self.select_button.clicked.connect(self.browse_folder)     # When the button is pressed execute browse_folder function
        self.download_button.clicked.connect(self.download_action) # When the button is pressed execute download_action function


    def browse_folder(self):
        directory = ""
        directory = QtGui.QFileDialog.getExistingDirectory(self, "Pick a folder")
        self.path_text.setText(directory)


    def download_action(self):
        link = ""
        path = ""
        link = str(self.link_text.text())
        path = self.fix_path(self.path_text.text())
        if self.checkFields(link, path):
            self.printInfo("Everything ok, starting download")
            self.printInfo("Link: " + link)
            self.printInfo("Path: " + path)
            ytdservice.main(link, path)
        else:
            self.printInfo("Error in one of the fields, that format is not supported or you left it empty")


    def fix_path(self, path):
        path = list(str("".join(str(path))))
        counter = 0
        for i in path:
            if i == "\\":
                path[counter] = "/"
            counter += 1
        return "".join(path)


    def checkFields(self, link, path):
        clink = False
        cpath = False
        if ("http://www.youtube.com/playlist?list=" in link) or ("https://www.youtube.com/playlist?list=" in link) or ("http://www.youtube.com/watch?v=" in link) or ("https://www.youtube.com/watch?v=" in link):
            clink = True
        if path != "":
            cpath = True
        return cpath and clink


    def printInfo(self, text):
        print text
        self.plainTextEdit.appendPlainText(str(text)) #No funciona


def main():
    app = QtGui.QApplication(sys.argv)
    form = App()
    form.show()
    app.exec_()
    sys.exit(app.exec_())


if __name__ == '__main__':
    main()
