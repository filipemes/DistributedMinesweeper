# ************************************************************************************
# Author: Rui S. Moreira
# Date: 20/02/2014
# ************************************************************************************
#REM Script usage: runclient <role> (where role should be: server / client)
#source ./setclientenv.sh
source ./setenv.sh client

#Run python on *build/classes* or *dist* directory
#cd ${ABSPATH2CLASSES}
cd ${ABSPATH2DIST}
clear
#Run python 3:
#python -m http.server 8000
#Run python 2.7:
python -m SimpleHTTPServer 8000

cd ${ABSPATH2SRC}/${JAVASCRIPTSPATH}
