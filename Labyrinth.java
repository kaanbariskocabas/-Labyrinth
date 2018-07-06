package labyrinth;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Scanner;
import java.util.Random;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JComponent;
public class Labyrinth extends JComponent{
    public static int kareKok;
    public static int dugumSayisi;
    public static int baslangicDugum;
    public static int hedefDugum;
    public static Integer [][] komsulukMatrisi = new Integer[500][500];
    public static Integer [] drawPath = new Integer[500]; 
    private static final String input = "test.txt";
    private static final String outRf = "outR.txt";
    private static final String outQf = "outQ.txt";
    private static final String outPathf = "outPath.txt";
     public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 1920, 1080);
        g.setColor(Color.BLACK);
        for (int i = 60; i <= kareKok*60; i+= 60){
            for (int j = 60; j <= kareKok*60; j+=60) {
                g.drawRect(i, j, 60, 60);
            }
        }
        int y=0;
        g.setColor(Color.WHITE);
        for (int i = 0; i < dugumSayisi; i++){
            for (int j = 0; j < dugumSayisi; j++) {
                if(komsulukMatrisi[i][j]==100&&i<j){
                    //System.out.println("komsulukMatrisi("+i+")("+j+") : "+komsulukMatrisi[i][j]);
                    if(j==i+1){
                        g.drawLine(120+(i%kareKok*60), 61+(y*60), 120+(i%kareKok*60), 119+(y*60));
                    }
                    else if(j==i+kareKok){
                        g.drawLine(61+(i%kareKok*60), 120+(y*60), 119+(i%kareKok*60), 120+(y*60));
                        //System.out.println("Burasi : "+i+" "+j+" "+y);
                    }
                }
            }
            if(i%kareKok==kareKok-1&&i!=0)
                        y++;
        }
        y=0;
        //System.out.println(baslangicDugum+" karekok : " +kareKok);
        g.setColor(Color.RED);
        g.fillOval( 85+((baslangicDugum%kareKok)*60),85+((baslangicDugum/kareKok)*60), 10, 10);
         for (int i = 0; i < dugumSayisi; i++) {
             y=drawPath[i]/kareKok;
             if(drawPath[i]!=-1&&drawPath[i]!=hedefDugum)
             {
                 if(drawPath[i]<drawPath[i+1]){
                     if(drawPath[i+1]==drawPath[i]+1){
                         g.drawLine(90+(drawPath[i]%kareKok*60), 90+(y*60), 150+(drawPath[i]%kareKok*60), 90+(y*60));
                     }
                     if(drawPath[i+1]==drawPath[i]+kareKok){
                         g.drawLine(90+(drawPath[i]%kareKok*60), 90+(y*60), 90+(drawPath[i]%kareKok*60), 150+(y*60));
                     }
                 }
                 else if(drawPath[i]>drawPath[i+1]){
                     if(drawPath[i]==drawPath[i+1]+1){
                         g.drawLine(90+(drawPath[i]%kareKok*60), 90+(y*60), 30+(drawPath[i]%kareKok*60), 90+(y*60));
                     }
                     if(drawPath[i]==drawPath[i+1]+kareKok){
                         g.drawLine(90+(drawPath[i]%kareKok*60), 90+(y*60), 90+(drawPath[i]%kareKok*60), 30+(y*60));
                     }
             }
             }
         }
    }

  public static void main(String[] args) {
        
        BufferedReader br = null;
	FileReader fr = null;
        BufferedWriter bw = null;
	FileWriter fw = null;
        try {
    
            fr = new FileReader(input);
            
            br = new BufferedReader(fr);
            
            int i = 0;
            String [] str= new String[100];
            
            while ((str[i] = br.readLine()) != null) {
                            //String[] ar=str.split(",");
                            //System.out.println(str[i]); 
                            i++;                   
            }
            int j=0;
            int a=0,son=0;
            String [] str1 =new String[500];
            String [][] strMatris = new String[500][500];
            
            while(j<i)
            {
                 String [] ar = str[j].split(",");
                 //if(ar[1]!= null)
                 
                for (int k = 0; k < ar.length; k++) {
                    strMatris[j][k] = ar[k];
                    str1[a]=ar[k];
                    a++;
                }
                son = a;
                 j++;
            }
            for (int k = 0; k < strMatris.length; k++) {
                for (int l = 0; l < strMatris[k].length; l++) {
                    if(strMatris[k][l]!=null)
                    {
                        //intMatris[k][l] = Integer.valueOf(strMatris[k][l]);
                        //System.out.println("STRM("+k+")"+"("+l+")"+strMatris[k][l]);

                    }

                }if(strMatris[k][0]!=null)
                    {
                       dugumSayisi++;
                    }
            }
            kareKok = (int) Math.sqrt(dugumSayisi);
            Scanner keyboard = new Scanner(System.in);
            System.out.print("Baslangic Dugumunu Giriniz : ");
            baslangicDugum = keyboard.nextInt();
            System.out.print("Hedef Dugumu Giriniz : ");
            hedefDugum = keyboard.nextInt();
            int bulunulanDugum = baslangicDugum;
            
            Integer [][] intMatris = new Integer[dugumSayisi][dugumSayisi];
            Integer [][] rMatris = new Integer[dugumSayisi][dugumSayisi];
            Integer [][] durumAksiyonMatris = new Integer[dugumSayisi][dugumSayisi];
            double [][] qMatris = new double[dugumSayisi][dugumSayisi];
            double [][] max = new double [dugumSayisi][dugumSayisi];
            int [] pathMax = new int[dugumSayisi];
            int [] path = new int[dugumSayisi];
            for (int  k = 0; k < dugumSayisi; k++) {
                path[k]=-1;
                drawPath[k]=-1;
                pathMax[k]=0;
                for (int l = 0; l < rMatris.length; l++) {
                    rMatris[k][l]=-1;
                    durumAksiyonMatris[k][l]=0;
                    qMatris[k][l]=0;
                    max[k][l]=0;
                    komsulukMatrisi[k][l]=0;
                }
            }
            for (int k = 0; k < 500; k++) {
                drawPath[k]=-1;
                
            }
            for (int k = 0; k < dugumSayisi; k++) {
                for (int l = 0; l < dugumSayisi; l++) {
                     
                     if(strMatris[k][l]!=null)
                    {
                        intMatris[k][l] = Integer.valueOf(strMatris[k][l]);
                        durumAksiyonMatris[k][intMatris[k][l]]=100;
                       //System.out.println("INTM("+k+")"+"("+l+")"+intMatris[k][l]);
                       //System.out.println("durumAksyon("+k+")"+"("+intMatris[k][l]+")"+durumAksiyonMatris[k][l]);
                       if(intMatris[k][l]==hedefDugum )
                       rMatris[k][intMatris[k][l]]=100;
                       else
                       rMatris[k][intMatris[k][l]]=0;
                    }
                    //System.out.println(rMatris[k][l]);
                }
        }
            rMatris[hedefDugum][hedefDugum]=100;
            durumAksiyonMatris[hedefDugum][hedefDugum]=100;
            Random rand = new Random();
            int randDugum;
            double [] maximum=new double[dugumSayisi];
            randDugum = rand.nextInt(dugumSayisi);
            int iterasyon=0;
            while(iterasyon<=3000){
                randDugum = rand.nextInt(dugumSayisi);
                if (durumAksiyonMatris[bulunulanDugum][randDugum]==100) {
                    //System.out.println("durumAksiyonMatris("+bulunulanDugum+")("+randDugum+") : "+durumAksiyonMatris[bulunulanDugum][randDugum]);
                    for (int k = 0; k < dugumSayisi; k++) {
                        if(durumAksiyonMatris[randDugum][k]==100)
                        {
                            if(qMatris[randDugum][k]>maximum[randDugum]&&randDugum!=bulunulanDugum)
                            maximum[randDugum]=qMatris[randDugum][k];
                            
                        }
                    }
                    qMatris[bulunulanDugum][randDugum] = rMatris[bulunulanDugum][randDugum]+(8*maximum[randDugum]/10);
                    //System.out.println("qMatris("+bulunulanDugum+")("+randDugum+") : "+qMatris[bulunulanDugum][randDugum]);
                    
                bulunulanDugum=randDugum;
                iterasyon++;
                }
               /* if(bulunulanDugum==hedefDugum)
                break;*/
            }
            double maximumDeger=0;
            for (int k = 0; k < dugumSayisi; k++) {
                
                for (int l = 0; l < dugumSayisi; l++) {
                    //System.out.println("qMatris("+k+")("+l+") : "+qMatris[k][l]);
                    if(qMatris[k][l]>maximumDeger){
                     maximumDeger=qMatris[k][l];   
                     pathMax[k]=l;
                     
                    }
                    
                //System.out.println("pathMax("+k+") : "+pathMax[k]);
                }maximumDeger=0;
            }
            int sayac=0;
            bulunulanDugum=baslangicDugum;
            while(true)
            {
               //System.out.println("pathMax("+sayac+") : "+pathMax[sayac]);
               path[sayac]=bulunulanDugum;
               //System.out.println("hedef :"+hedefDugum);
               //System.out.println("path("+sayac+") : "+path[sayac]);
               sayac++;
               if(bulunulanDugum==hedefDugum)
                   break;
               else
               bulunulanDugum=pathMax[bulunulanDugum];
               
            }
            
            //OUTPUT
            
            PrintStream outR = new PrintStream(new FileOutputStream(outRf));
            PrintStream outQ = new PrintStream(new FileOutputStream(outQf));
            PrintStream outPath = new PrintStream(new FileOutputStream(outPathf));
            outR.print("R Matrisi");
            outR.println();
            for (int k = 0; k < dugumSayisi; k++) {
                for (int l = 0; l < dugumSayisi; l++) {
                    outR.print(rMatris[k][l]+" ");
                }
                outR.println();   
            }
            outQ.print("Q Matrisi 3000 iterasyon sonrasi");
            outQ.println();
            for (int k = 0; k < dugumSayisi; k++) {
                for (int l = 0; l < dugumSayisi; l++) {
                    if(qMatris[k][l]==0)
                        outQ.print("0 ");
                    else
                    outQ.print(Math.floor(qMatris[k][l] * 100) / 100+" ");
                }
                outQ.println();
            }
           outPath.print("Printing Path");
           outPath.println();
            for (int k = 0; k < path.length-1; k++) {
                if(path[k]!=-1)
                outPath.print(path[k]+" ");
            }
            for (int c = 0; c < dugumSayisi; c++) {
                drawPath[c]=path[c];
            for (int d = 0; d < dugumSayisi; d++) {
                komsulukMatrisi[c][d]=durumAksiyonMatris[c][d];
                if (komsulukMatrisi[c][d]==100) {
                    //System.out.println("komsulukMatrisi("+c+")("+d+") : "+komsulukMatrisi[c][d]);
            }
        }   
        }
        }
        catch (IOException e) {
        e.printStackTrace();
        }
        JFrame window = new JFrame();
        window.setTitle("LABIRENT");
        window.setSize(1920,1080);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().add(new Labyrinth());
        window.setVisible(true);
    }
}
