package logic;

import model.Order;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class CsvReader {

    public static TransferObject readCsv(List<String> fileNames) throws IOException {
        List<Order> orders = new ArrayList<>();

        ArrayList<WrongFile> wrongFiles=new ArrayList<>();
        BufferedReader br = null;
        Order o = new Order();
        String strLine;
        StringTokenizer st = null;
        Boolean error;
        int lineNumber = 1;
        for (String strFile : fileNames) {
            ArrayList<Integer> wrongRecordsList=new ArrayList<>();
            lineNumber = 1;
            try {
                br = new BufferedReader(new FileReader(strFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            while ((strLine = br.readLine()) != null) {
                error=false;
                strFile.split(",");
                if (lineNumber == 1) {
                    //check columns
                } else {
                    st = new StringTokenizer(strLine, ",");
                    while (st.hasMoreTokens()) {
                    try {
                            o = new Order(st.nextToken(),
                                    Long.parseLong(st.nextToken()),
                                    st.nextToken(),
                                    Integer.parseInt(st.nextToken()),
                                    Double.parseDouble(st.nextToken()));
                            orders.add(o);
                    } catch (Exception e) {
                        error=true;

                    }
                }

            }
            if (error) {
                wrongRecordsList.add(lineNumber);
            }
            lineNumber++;

        }
        br.close();
            if (!wrongRecordsList.isEmpty()){
                WrongFile wrongFile = new WrongFile(strFile,wrongRecordsList);
                wrongFiles.add(wrongFile);
            }

    }
    TransferObject transferObject=new TransferObject(orders,wrongFiles);
        return transferObject;
}
}
