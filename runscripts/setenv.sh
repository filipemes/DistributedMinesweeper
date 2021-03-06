# ************************************************************************************
# Description: run previously all batch files
# Author: Rui S. Moreira
# Date: 20/02/2014
# ************************************************************************************

# ======================== Use Shell Parameters ========================
# Script usage: setenv <role> (where role should be: server / client)
export SCRIPT_ROLE=$1

# ======================== CHANGE BELOW ACCORDING YOUR PROJECT and PC SETTINGS ========================
# ==== PC STUFF ====


# ==== JAVA NAMING STUFF ====
export JAVAPROJ_NAME=DistributedMinesweeper
export JAVAPROJ=$(pwd)/..
export PACKAGE=Minesweeper
export SERVICE_NAME_ON_REGISTRY=MinesweeperService
export CLIENT_CLASS_PREFIX=Minesweeper
export SERVER_CLASS_PREFIX=Minesweeper
export CLIENT_CLASS_POSTFIX=Client
export SERVER_CLASS_POSTFIX=Server
export SETUP_CLASS_POSTFIX=Setup
export SERVANT_IMPL_CLASS_POSTFIX=Impl
export SERVANT_ACTIVATABLE_IMPL_CLASS_POSTFIX=ActivatableImpl

# ==== NETWORK STUFF ====
# Must run http server on codebase host:
# Python 2: python -m SimpleHTTPServer 8000
# Python 3: python -m http.server 8000
export REGISTRY_HOST=localhost
export REGISTRY_PORT=1099
export SERVER_RMI_HOST=${REGISTRY_HOST}
export SERVER_RMI_PORT=1098
export SERVER_CODEBASE_HOST=${SERVER_RMI_HOST}
export SERVER_CODEBASE_PORT=8000
export CLIENT_RMI_HOST=${REGISTRY_HOST}
export CLIENT_RMI_PORT=1097
export CLIENT_CODEBASE_HOST=${CLIENT_RMI_HOST}
export CLIENT_CODEBASE_PORT=8000

# ======================== DO NOT CHANGE AFTER THIS POINT ========================
export JAVAPACKAGE=${PACKAGE}
export JAVAPACKAGEROLE=${PACKAGE}.${SCRIPT_ROLE}
export JAVAPACKAGEPATH=${PACKAGE}/${SCRIPT_ROLE}
export JAVASCRIPTSPATH=${PACKAGE}/runscripts
export JAVASECURITYPATH=${PACKAGE}/securitypolicies
export SERVICE_NAME=${SERVICE_PREFIX}Service
export SERVICE_URL=rmi://${REGISTRY_HOST}:${REGISTRY_PORT}/${SERVICE_NAME}
export SERVANT_ACTIVATABLE_IMPL_CLASS=${JAVAPACKAGEROLE}.${SERVER_CLASS_PREFIX}${SERVANT_ACTIVATABLE_IMPL_CLASS_POSTFIX}
export SERVANT_PERSISTENT_STATE_FILENAME=${SERVICE_PREFIX}Persistent.State

export NETBEANS_CLASSES=build/classes/
export NETBEANS_SRC=src
export NETBEANS_DIST=dist
export NETBEANS_DIST_LIB=lib

export JAVAPROJ_CLASSES_FOLDER=${JAVAPROJ}/${NETBEANS_CLASSES}
export JAVAPROJ_DIST_FOLDER=${JAVAPROJ}/${NETBEANS_DIST}
export JAVAPROJ_DIST_LIB_FOLDER=${JAVAPROJ}/${NETBEANS_DIST_LIB}
export JAVAPROJ_JAR_FILE=${JAVAPROJ_NAME}.jar
export MYSQL_CON_JAR=mysql-connector-java-5.1.38-bin.jar

export CLASSPATH=.:${JAVAPROJ_CLASSES_FOLDER}
#export CLASSPATH=.:${JAVAPROJ_DIST_FOLDER}/${JAVAPROJ_JAR_FILE}:${JAVAPROJ_DIST_LIB_FOLDER}/${MYSQL_CON_JAR}

export ABSPATH2CLASSES=${JAVAPROJ}/${NETBEANS_CLASSES}
export ABSPATH2SRC=${JAVAPROJ}/${NETBEANS_SRC}
export ABSPATH2DIST=${JAVAPROJ}/${NETBEANS_DIST}

#java.rmi.server.codebase property defines the location where the client/server provides its classes.
#export CODEBASE=file:///${JAVAPROJ}/${NETBEANS_CLASSES}
#export SERVER_CODEBASE=http://${SERVER_CODEBASE_HOST}:${SERVER_CODEBASE_PORT}/${NETBEANS_CLASSES}
#export CLIENT_CODEBASE=http://${CLIENT_CODEBASE_HOST}:${CLIENT_CODEBASE_PORT}/${NETBEANS_CLASSES}
#With several JARS: http://${SERVER_CODEBASE_HOST}:${SERVER_CODEBASE_PORT}/${MYSQL_CON_JAR}
export SERVER_CODEBASE=http://${SERVER_CODEBASE_HOST}:${SERVER_CODEBASE_PORT}/${JAVAPROJ_JAR_FILE}
export CLIENT_CODEBASE=http://${CLIENT_CODEBASE_HOST}:${CLIENT_CODEBASE_PORT}/${JAVAPROJ_JAR_FILE}

#Policy tool editor: /Library/Java/JavaVirtualMachines/jdk1.8.0_25.jdk/Contents/Home/bin/policytool
export SERVER_SECURITY_POLICY=file:///${JAVAPROJ}/${NETBEANS_SRC}/${JAVASECURITYPATH}/serverAllPermition.policy
export CLIENT_SECURITY_POLICY=file:///${JAVAPROJ}/${NETBEANS_SRC}/${JAVASECURITYPATH}/clientAllPermition.policy
export SETUP_SECURITY_POLICY=file:///${JAVAPROJ}/${NETBEANS_SRC}/${JAVASECURITYPATH}/setup.policy
export RMID_SECURITY_POLICY=file:///${JAVAPROJ}/${NETBEANS_SRC}/${JAVASECURITYPATH}/rmid.policy
export GROUP_SECURITY_POLICY=file:///${JAVAPROJ}/${NETBEANS_SRC}/${JAVASECURITYPATH}/group.policy
