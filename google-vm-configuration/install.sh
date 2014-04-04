#!/bin/bash

sudo apt-get install vim
sudo apt-get install openjdk-7-jdk

wget http://mirrors.ircam.fr/pub/apache/maven/maven-3/3.2.1/binaries/apache-maven-3.2.1-bin.tar.gz
tar xvzf apache-maven-3.2.1-bin.tar.gz
sudo mv apache-maven-3.2.1 /usr/local
