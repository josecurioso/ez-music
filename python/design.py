# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'design.ui'
#
# Created by: PyQt4 UI code generator 4.11.4
#
# WARNING! All changes made in this file will be lost!

from PyQt4 import QtCore, QtGui

try:
    _fromUtf8 = QtCore.QString.fromUtf8
except AttributeError:
    def _fromUtf8(s):
        return s

try:
    _encoding = QtGui.QApplication.UnicodeUTF8
    def _translate(context, text, disambig):
        return QtGui.QApplication.translate(context, text, disambig, _encoding)
except AttributeError:
    def _translate(context, text, disambig):
        return QtGui.QApplication.translate(context, text, disambig)

class Ui_MainWindow(object):
    def setupUi(self, MainWindow):
        MainWindow.setObjectName(_fromUtf8("MainWindow"))
        MainWindow.resize(436, 274)
        sizePolicy = QtGui.QSizePolicy(QtGui.QSizePolicy.Minimum, QtGui.QSizePolicy.Minimum)
        sizePolicy.setHorizontalStretch(0)
        sizePolicy.setVerticalStretch(0)
        sizePolicy.setHeightForWidth(MainWindow.sizePolicy().hasHeightForWidth())
        MainWindow.setSizePolicy(sizePolicy)
        MainWindow.setMinimumSize(QtCore.QSize(436, 274))
        MainWindow.setMaximumSize(QtCore.QSize(436, 274))
        icon = QtGui.QIcon()
        icon.addPixmap(QtGui.QPixmap(_fromUtf8("play.ico")), QtGui.QIcon.Normal, QtGui.QIcon.Off)
        MainWindow.setWindowIcon(icon)
        self.centralwidget = QtGui.QWidget(MainWindow)
        self.centralwidget.setObjectName(_fromUtf8("centralwidget"))
        self.download_button = QtGui.QPushButton(self.centralwidget)
        self.download_button.setGeometry(QtCore.QRect(130, 70, 75, 23))
        self.download_button.setAutoDefault(False)
        self.download_button.setDefault(False)
        self.download_button.setFlat(False)
        self.download_button.setObjectName(_fromUtf8("download_button"))
        self.link_text = QtGui.QLineEdit(self.centralwidget)
        self.link_text.setGeometry(QtCore.QRect(40, 10, 381, 20))
        self.link_text.setObjectName(_fromUtf8("link_text"))
        self.label = QtGui.QLabel(self.centralwidget)
        self.label.setGeometry(QtCore.QRect(10, 10, 46, 13))
        self.label.setObjectName(_fromUtf8("label"))
        self.path_text = QtGui.QLineEdit(self.centralwidget)
        self.path_text.setGeometry(QtCore.QRect(40, 40, 281, 20))
        self.path_text.setReadOnly(True)
        self.path_text.setObjectName(_fromUtf8("path_text"))
        self.label_2 = QtGui.QLabel(self.centralwidget)
        self.label_2.setGeometry(QtCore.QRect(10, 40, 46, 13))
        self.label_2.setObjectName(_fromUtf8("label_2"))
        self.select_button = QtGui.QPushButton(self.centralwidget)
        self.select_button.setGeometry(QtCore.QRect(340, 40, 75, 23))
        self.select_button.setObjectName(_fromUtf8("select_button"))
        self.plainTextEdit = QtGui.QPlainTextEdit(self.centralwidget)
        self.plainTextEdit.setEnabled(True)
        self.plainTextEdit.setGeometry(QtCore.QRect(10, 110, 411, 121))
        self.plainTextEdit.setReadOnly(True)
        self.plainTextEdit.setObjectName(_fromUtf8("plainTextEdit"))
        MainWindow.setCentralWidget(self.centralwidget)
        self.menubar = QtGui.QMenuBar(MainWindow)
        self.menubar.setGeometry(QtCore.QRect(0, 0, 436, 21))
        self.menubar.setObjectName(_fromUtf8("menubar"))
        MainWindow.setMenuBar(self.menubar)
        self.statusbar = QtGui.QStatusBar(MainWindow)
        self.statusbar.setObjectName(_fromUtf8("statusbar"))
        MainWindow.setStatusBar(self.statusbar)

        self.retranslateUi(MainWindow)
        QtCore.QMetaObject.connectSlotsByName(MainWindow)

    def retranslateUi(self, MainWindow):
        MainWindow.setWindowTitle(_translate("MainWindow", "ez music", None))
        self.download_button.setText(_translate("MainWindow", "Download", None))
        self.label.setText(_translate("MainWindow", "Link:", None))
        self.label_2.setText(_translate("MainWindow", "Path: ", None))
        self.select_button.setText(_translate("MainWindow", "Select", None))

