package Final;

import gnu.io.*;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.TooManyListenersException;

public class Model {

    //for containing the ports that will be found
    Enumeration ports = null;
    //map the port names to CommPortIdentifiers
    HashMap portMap = new HashMap();

    //this is the object that contains the opened port
    CommPortIdentifier selectedPortIdentifier = null;
    SerialPort serialPort = null;

    //input and output streams for sending and receiving data
    InputStream input = null;
    OutputStream output = null;

    //just a boolean flag that i use for enabling
    //and disabling buttons depending on whether the program
    //is connected to a serial port or not
    boolean bConnected = false;

    //the timeout value for connecting with the port
    final static int TIMEOUT = 2000;

    //some ascii values for for certain things
    final static int SPACE_ASCII = 32;
    final static int DASH_ASCII = 45;
    final static int NEW_LINE_ASCII = 10;

    //a string for recording what goes on in the program
    //this string is written to the GUI
}