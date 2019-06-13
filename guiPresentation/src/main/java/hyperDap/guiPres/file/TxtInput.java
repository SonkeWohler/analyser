package hyperDap.guiPres.file;

import hyperDap.base.types.dataSet.ValueDataSet;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TxtInput {

    private String fileName="data.txt";
    private File file;
    private String splitChar="\t";

    public TxtInput() {
        file= new File("D:/EclipseLibrary/Honours/analyser/guiPresentation/target/classes/hyperDap/guiPres/data/data.txt");
        // file = new File(String.valueOf(getClass().getResource("/hyperDap/guiPres/data/" + this.fileName)));
    }

    public ValueDataSet<Double> readFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line= reader.readLine();
        String[] reads= line.split("\t");
        double x= Double.parseDouble(reads[0]);
        double y=Double.parseDouble(reads[1]);
        line=reader.readLine();
        reads=line.split("\t");
        ValueDataSet<Double> set= new ValueDataSet<Double>(x,Double.parseDouble(reads[0])-x,0.1,d -> Double.valueOf(d));
        set.add(y);
        set.add(Double.parseDouble(reads[1]));
        while ((line=reader.readLine())!=null){
            set.add(Double.parseDouble(line.split("\t")[1]));
        }
        return set;
    }

}
