package Minesweeper.server;

import Minesweeper.util.rmisetup.SetupContextRMI;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * Title: Project DS</p>
 * <p>
 * <p>
 * Copyright: Copyright (c) 2017</p>
 * <p>
 * Company: UFP </p>
 *
 * @author
 * @version 3.0
 */
public class MinesweeperServer {

    /**
     * Context for running a RMI Servant on a host
     */
    private SetupContextRMI contextRMI;

    /**
     * Remote interface that will hold reference to the Servant impl
     */
    private MinesweeperFactoryRI factoryRI;



    public static void main(String[] args) throws RemoteException {
        if (args != null && args.length < 3) {
            System.err.println("usage: java [options] MinesweeperServer <rmi_registry_ip> <rmi_registry_port> <service_name>");
            System.exit(-1);
        } else if (args.length == 3) {
            //1. ============ Create Servant ============
            MinesweeperServer hws = new MinesweeperServer(args);
            //2. ============ Rebind servant on rmiregistry ============
            hws.rebindService();
        } 

    }

    /**
     *
     * @param args
     */
    public MinesweeperServer(String args[]) {
        try {
            //============ List and Set args ============
            printArgs(args);
            String registryIP = args[0];
            String registryPort = args[1];
            String serviceName = args[2];
            System.out.println(registryPort);
            //============ Create a context for RMI setup ============
            this.contextRMI = new SetupContextRMI(this.getClass(), registryIP, registryPort, new String[]{serviceName});
        } catch (RemoteException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    private MinesweeperFactoryRI lookupService() {
        try {
            //Get proxy to rmiregistry
            Registry registry = this.contextRMI.getRegistry();
            //Lookup service on rmiregistry and wait for calls
            if (registry != null) {
                //Get service url (including servicename)
                String serviceUrl = this.contextRMI.getServicesUrl(0);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going to lookup service @ {0}", serviceUrl);

                //============ Get proxy to calculater service ============
                this.factoryRI = (MinesweeperFactoryRI) registry.lookup(serviceUrl);

            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "registry not bound (check IPs). :(");
                //registry = LocateRegistry.createRegistry(1099);
            }
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return factoryRI;
    }

    void rebindService() throws RemoteException {
        //Get proxy to rmiregistry
        Registry registry = contextRMI.getRegistry();
        //Bind service on rmiregistry and wait for calls
        if (registry != null) {
            //============ Create Servant ============
            this.factoryRI = (MinesweeperFactoryRI) new MinesweeperFactoryImpl();

            //Get service url (including servicename)
            String serviceUrl = this.contextRMI.getServicesUrl(0);
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going to rebind service @ {0}", serviceUrl);

            //============ Rebind servant ============
            registry.rebind(serviceUrl, this.factoryRI);
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "service bound and running. :)");
        } else {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "registry not bound (check IPs). :(");
            //registry = LocateRegistry.createRegistry(1099);
        }
    }

    private void printArgs(String args[]) {
        for (int i = 0; args != null && i < args.length; i++) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "args[{0}] = {1}", new Object[]{i, args[i]});
        }
    }
}
