package edu.buffalo.cse.cse486586.groupmessenger2;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.widget.TextView;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import java.io.BufferedReader;

import java.io.OptionalDataException;
import java.io.PrintWriter;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TextView;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.Executor;

import static android.os.AsyncTask.*;

/**
 * GroupMessengerActivity is the main Activity for the assignment.
 * 
 * @author stevko
 *
 */
public class GroupMessengerActivity extends Activity {

    static final String TAG = GroupMessengerActivity.class.getSimpleName();
    static final int SERVER_PORT = 10000;
    String[] remotePort = {"11108", "11112", "11116", "11120", "11124"};
    PriorityQueue<String> holdback_queue = new PriorityQueue<>(); //Priority queue
    //Queue<Serialization> order_queue = new LinkedList<Serialization>();
    private ContentResolver cr;
    private Uri ur;
    private ContentValues cv;
    float counter = 0.0F;
    int sg = 0;
    int rg = 0;
    String myPort = null;
    float pid = 0.0F;
    int upi;
    int seq;

    String deliverable="false";
    HashMap<Integer,String> Msglist = new HashMap<>();

    private Uri buildUri(String scheme, String authority) {
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.authority(authority);
        uriBuilder.scheme(scheme);
        return uriBuilder.build();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_messenger);
        ur = buildUri("content",
                "edu.buffalo.cse.cse486586.groupmessenger2.provider");

        /*
         * TODO: Use the TextView to display your messages. Though there is no grading component
         * on how you display the messages, if you implement it, it'll make your debugging easier.
         */
        TelephonyManager tel = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String portStr = tel.getLine1Number().substring(tel.getLine1Number().length() - 4);
        myPort = String.valueOf((Integer.parseInt(portStr) * 2));
        TextView tv = (TextView) findViewById(R.id.textView1);
        tv.setMovementMethod(new ScrollingMovementMethod());
         /*
         * Registers OnPTestClickListener for "button1" in the layout, which is the "PTest" button.
         * OnPTestClickListener demonstrates how to access a ContentProvider.
         */
        findViewById(R.id.button1).setOnClickListener(
                new OnPTestClickListener(tv, getContentResolver()));
         /*
         * TODO: You need to register and implement an OnClickListener for the "Send" button.
         * In your implementation you need to get the message from the input box (EditText)
         * and send it to other AVDs.
         */
        try {
            /*
             * Create a server socket as well as a thread (AsyncTask) that listens on the server
             * port.
             *Object
             * AsyncTask is a simplified thread construct that Android provides. Please make sure
             * you know how it works by reading
             * http://developer.android.com/reference/android/os/AsyncTask.html
             */
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            new ServerTask().executeOnExecutor(THREAD_POOL_EXECUTOR, serverSocket);
        } catch (IOException e) {
            Log.e(TAG, "Can't create a ServerSocket");
            return;
        }
        /*
         * Retrieve a pointer to the input box (EditText) defined in the layout
         * XML file (res/layout/main.xml).
         *
         * This is another example of R class variables. R.id.edit_text refers to the EditText UI
         * element declared in res/layout/main.xml. The id of "edit_text" is given in that file by
         * the use of "android:id="@+id/edit_text""
         */
        final EditText editText = (EditText) findViewById(R.id.editText1);
        findViewById(R.id.button4).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                //String tempport = Integer.parseInt(myPort);

                /* check the port number and assign a number 0.1 to port number 11108 etc(append counter with this value to give proposed value)*/

                //Serialization msg = new Serialization(editText.getText().toString()
                  //      ,proposedvalue,myPort);
                String msg= editText.getText().toString() + "\n";
                //counter++;
                //msg.setCurrentvalue(msg.getMessage_id());
                String msgparam = msg+":"+myPort;
                editText.setText("");

                     /*
                     * Note that the following AsyncTask uses AsyncTask.SERIAL_EXECUTOR, not
                     * AsyncTask.THREAD_POOL_EXECUTOR as the above ServerTask does. To understand
                     * the difference, please take a look at
                     * http://developer.android.com/reference/android/os/AsyncTask.html
                     */
                new ClientTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, msgparam);
            }

        });
    }

    private class ClientTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... msgs) {
            try {

                for (int i = 0; i < 5; i++) {


                    Socket socket = new Socket(
                            InetAddress
                                    .getByAddress(new byte[]{10, 0, 2, 2}),
                            Integer.parseInt(remotePort[i]));

					/*
					 * Here i am reading the input via the buffer and writing it
					 * to the buffer in the socket Then i am sending the data
					 * via the socket to the server
					 */
                    /*ObjectOutputStream msgbuffer = null;
                    msgbuffer = new ObjectOutputStream(socket.getOutputStream());
                    msgbuffer.writeObject(msgs[0]);
                    String s = "proposed";
                    msgbuffer.writeObject(s);
                    msgbuffer.flush();
                    msgbuffer.close();

                    //Msglist.put(msgs[0].getMessage_id(),msgs[0]);
                    socket.close();*/
                    String s="initial";
                   String msgToSend = msgs[0]+"="+s+"-"+deliverable+"#";

                /*
                 * TODO: Fill in your client code that sends out a message.
                 */
                    PrintWriter printwriter = new PrintWriter(socket.getOutputStream(), true);
                    printwriter.write(msgToSend);

                   // printwriter.write(s);
                    printwriter.flush();
                    printwriter.close();
                    socket.close();
                 /*
                 * TODO: Fill in your client code that sends out a message.
                 */
                }
                counter++;
            } catch (Exception e) {
                Log.e(TAG, "ClientTask UnknownHostException"+Log.getStackTraceString(e));

            }

            return null;
        }

    }

    private class ServerTask extends AsyncTask<ServerSocket, String, Void> {

        @Override
        protected Void doInBackground(ServerSocket... sockets) {
            ServerSocket serverSocket = sockets[0];

            while (true) {

                //Serialization tempmsg = null;
                //ObjectInputStream ois = null;
                try {

                    Socket temmpsock = serverSocket.accept();
                    InputStreamReader inputStreamReader = new InputStreamReader(temmpsock.getInputStream());
                    //ois = new ObjectInputStream(
                    //      temmpsock.getInputStream());
                    //InputStreamReader inputStreamReader = new InputStreamReader(temmpsock.getInputStream());
                    //tempmsg = (Serialization) ois.readObject();
                    // int tempport = Integer.parseInt(myPort);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String message = bufferedReader.readLine();
                    String msgtype = message.substring(message.indexOf("="), message.indexOf("-"));
                    String myPort1 = message.substring(message.indexOf(":"), message.indexOf("="));
                    String mes=message.substring(message.indexOf(":"));



                    if (msgtype.equals("initial")) {
                        //Proposed value to be sent to the sender logic and update the tempmsg with the proposed value
                        // counter++;
                        double proposedvalue = 0.0;
                        if (myPort1.equalsIgnoreCase(remotePort[0])) {
                            pid = 0.0F;
                            proposedvalue = counter + pid + 1;
                        } else if (myPort1.equalsIgnoreCase(remotePort[1])) {
                            pid = 0.1F;
                            proposedvalue = counter + pid + 1;
                        } else if (myPort1.equalsIgnoreCase(remotePort[2])) {
                            pid = 0.2F;
                            proposedvalue = counter + pid + 1;
                        } else if (myPort1.equalsIgnoreCase(remotePort[3])) {
                            pid = 0.3F;
                            proposedvalue = counter + pid + 1;
                        } else {
                            pid = 0.4F;
                            proposedvalue = counter + pid + 1;
                        }
                        counter++;
                        //proposedvalue=counter+pid;
                        Socket socket = new Socket(
                                InetAddress
                                        .getByAddress(new byte[]{10, 0, 2, 2}),
                                Integer.parseInt(myPort1));

                        //ObjectOutputStream msgbuffer = null;
                        //msgbuffer = new ObjectOutputStream(socket.getOutputStream());
                        //msgbuffer.writeObject(message);
                        String s = "proposedback";
                        // msgbuffer.writeObject(s);
                        //msgbuffer.flush();
                        //msgbuffer.close();
                        //socket.close();
                        // String msgb=message+":"+myPort1+"="+s+"-"+deliverable+"#"+proposedvalue;
                        String pvs = String.valueOf(proposedvalue);
                        String msg1=mes+":"+myPort1+"="+s+"-"+deliverable+"#"+proposedvalue;
                        PrintWriter printwriter = new PrintWriter(socket.getOutputStream(), true);
                        printwriter.write(msg1);
                        printwriter.flush();
                        printwriter.close();
                        socket.close();
                        holdback_queue.add(msg1);
                        upi=((int)(pid*10));

                        Msglist.put(upi,msg1);


                    } else if (msgtype.equals("proposedback")) {
                        //check tempmsg and extract unique id. Use this , id to get the entry from hashmap
                        // Take the current value from the hashmap msg and compare it with received msg proposed value
                        //Update the current value with proposed value, if cv<pv (Set the max value of seq number)
                        //msg from hashmap's counter ++
                        //if counter =4, deliverable = true
                        //Priority queue insertion if the final value is set (Use comparable method in Serialization class)
                        //Remove the first element in the queue and publish the msg with the final value
                        //Insert msges to content provider (publish progress)
                        //Mulitcast the msg to all avd's except me(Socket logic)
                        double proposedvalue= Double.parseDouble(message.substring(message.indexOf("#")+1));
                        seq=((int)(proposedvalue/10));
                        int a[]= {seq};
                        int ctr=0;
                        ctr=ctr+1;
                        if (ctr==4)
                        {
                            deliverable="true";
                            int max = a[0];
                            for (int ktr = 0; ktr < a.length; ktr++) {
                                if (a[ktr] > max) {
                                    max =a[ktr];
                                }
                            }

                            Socket socket = new Socket(
                                    InetAddress
                                            .getByAddress(new byte[]{10, 0, 2, 2}),
                                    Integer.parseInt(myPort1));

                            String s="final";
                            String msgToSend = mes+":"+myPort1+"="+s+"-"+deliverable+"#"+max;

                            PrintWriter printwriter = new PrintWriter(socket.getOutputStream(), true);
                            printwriter.write(msgToSend);

                            // printwriter.write(s);
                            printwriter.flush();
                            printwriter.close();
                            socket.close();
                        }




                    } else if (msgtype.equals("final")) {
                        //Update the finalvalue of avd in holdback queue and set deliverable as true

                        Socket tempsocket = serverSocket.accept(); // accept the client connection
                        inputStreamReader = new InputStreamReader(tempsocket.getInputStream());
                        bufferedReader = new BufferedReader(inputStreamReader);
                        String message2 = bufferedReader.readLine();
                        holdback_queue.add(message);
                        cv = new ContentValues();
                        cr = getContentResolver();
                        cv.put("key",pid );
                        cv.put("value", message2);
                        cr.insert(ur,cv);
                        publishProgress(message2);
                        inputStreamReader.close();
                        tempsocket.close();
                        counter++;






                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //catch (UnknownHostException e) {
        //  e.printStackTrace();
        //} catch (IOException e) {
        //       e.printStackTrace();
        //}


        protected void onProgressUpdate(String... strings) {
            String strReceived = strings[0].trim();
            TextView remoteTextView = (TextView) findViewById(R.id.textView1);
            remoteTextView.append(strReceived + "\t\n");
            return;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_group_messenger, menu);
        return true;
    }
}
