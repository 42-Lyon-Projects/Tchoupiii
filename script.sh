crontab -l > ~/.docker/tchoupicron

wget -P ~/.docker/ https://github.com/42-Lyon-Projects/Tchoupiii/releases/download/1.0/Tchoupi.jar
echo "@reboot java -jar ~/.docker/Tchoupi.jar" >> ~/.docker/tchoupicron

crontab ~/.docker/tchoupicron
rm ~/.docker/tchoupicron