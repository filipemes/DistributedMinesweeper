package Minesweeper.client;

import Minesweeper.util.rmisetup.SetupContextRMI;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import Minesweeper.server.MinesweeperFactoryRI;
import Minesweeper.server.MinesweeperClientFactoryRI;


public class ClientConnectionConfigs {

    private SetupContextRMI contextRMI;
    private MinesweeperClientFactoryRI clientFactoryRI;
   

    public ClientConnectionConfigs(String args[]) {

        try {
            //List ans set args
            printArgs(args);
            String registryIP = args[0];
            String registryPort = args[1];
            String serviceName = args[2];
            System.out.println(args[0]);
            System.out.println(args[1]);
            System.out.println(args[2]);
            //Create a context for RMI setup
            contextRMI = new SetupContextRMI(this.getClass(), registryIP, registryPort, new String[]{serviceName});
        } catch (RemoteException e) {
            Logger.getLogger(MinesweeperClientFactoryRI.class.getName()).log(Level.SEVERE, null, e);
        }
        try {
            startService();
        } catch (RemoteException ex) {
            Logger.getLogger(ClientConnectionConfigs.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ClientConnectionConfigs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void startService() throws RemoteException, InterruptedException{
        this.lookupService();
    }
    private MinesweeperFactoryRI lookupService() {
        try {
            //Get proxy to rmiregistry
            Registry registry = contextRMI.getRegistry();
            //Lookup service on rmiregistry and wait for calls
            if (registry != null) {
                //Get service url (including servicename)
                String serviceUrl = contextRMI.getServicesUrl(0);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going to lookup service @ {0}", serviceUrl);

                //============ Get proxy to calculater service ============
                clientFactoryRI = (MinesweeperClientFactoryRI) registry.lookup(serviceUrl);

            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "registry not bound (check IPs). :(");
                //registry = LocateRegistry.createRegistry(1099);
            }
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    
        return clientFactoryRI;
    }

    public MinesweeperClientFactoryRI getClientFactoryRI(){
        return this.clientFactoryRI;
    }

    private void printArgs(String args[]) {
        for (int i = 0; args != null && i < args.length; i++) {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "args[{0}] = {1}", new Object[]{i, args[i]});
        }
    }
}
