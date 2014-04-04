#!/bin/bash

sudo apt-get install git
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java7-installer

wget http://mirrors.ircam.fr/pub/apache/maven/maven-3/3.2.1/binaries/apache-maven-3.2.1-bin.tar.gz
tar xvzf apache-maven-3.2.1-bin.tar.gz
sudo mv apache-maven-3.2.1 /usr/local
