import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Date;

public class MatchParser {
	
	public static void main(String args[]) throws IOException, ArithmeticException{
		String[] urls = getURL();
		
		for(int i = 0; i<24 ; i++){
			System.out.println(urls[i]);
		}
		//change the file name
		//String fileName = "http://fie.org/fencers/Alanna-GOLDIE-22533/";
		URL url;
		url = new URL("http://fie.org/fencers/Alanna-GOLDIE-22533/");
		//String fileName = "Fencer - GOLDIE Alanna - CANADA - FIE - International Fencing Federation.html";
		//change the file name
		PrintWriter pw = new PrintWriter(new File("MatchDataGOLDIE.csv"));
		//change the file name
		PrintWriter pw2 = new PrintWriter(new File("PRTDataGOLDIE.txt"));
		
		String birthday = null, fencer = null;
		int numVict = 0, numDef = 0, tstot = 0, trtot = 0;
		
		
	        StringBuilder sb = new StringBuilder();
	        sb.append("Name;DOB;Age;V/D;TS;TR;Adversary;Country;Ranking;Table;Weapon;Sex;Category;City;Type;Date");
			sb.append('\n');
			
		InputStream is = url.openStream();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))){
            String line;
            int j = 1;
            String country = null, weapon = null, category = null, type = null, date = null, city = null, opponent = null, vd = null;
			String ranking = null;
            int ts = 0, tr = 0, age = 0, agemin = 0, agemax = 0;
            while ((line = br.readLine()) != null) {
            //System.out.println(line);
            if(line.contains("jumbotron__birthdate")){
            	String[] bdayInfo = line.split("<");
            	
            	birthday = bdayInfo[bdayInfo.length-2].substring(33);
            	//String[] switchbday = birthday.substring();
            	birthday = birthday.substring(6, 10) + "-" + birthday.substring(3,5) + "-" + birthday.substring(0,2);
            
            	
            }
            
            if(line.contains("history__event-name") && !(line.contains("<%=")))
            {
            	String[] compInfo = line.split(" ");
            	category = compInfo[compInfo.length-3];
            	weapon = compInfo[compInfo.length-1].substring(0,1);
            	type = compInfo[compInfo.length-5];
            	date = compInfo[compInfo.length-7];
            	city = compInfo[compInfo.length-8];
            	age = Integer.parseInt(date.substring(0,4)) - Integer.parseInt(birthday.substring(0,4));
            	if(Integer.parseInt(date.substring(5,7)) < Integer.parseInt(birthday.substring(5,7))){
            		age--;
            	}
            	else if(Integer.parseInt(date.substring(5,7)) == Integer.parseInt(birthday.substring(5,7))){
            		
            		if(Integer.parseInt(date.substring(8,10)) < Integer.parseInt(birthday.substring(8,10))){
            			age--;
            		}
            				
            	}
            
            	if(numVict == 0){
            		agemin = age;
            		agemax = age;
            	}
            	if(age < agemin){
            		agemin = age;
            	}
            	if(age > agemax){
            		agemax = age;
            	}
            	line = br.readLine();
            	line = br.readLine();
            	
            	if(line.contains("winner_yes")){
            		vd = "V";
            		numVict++;
            	}
            	else {
            		vd = "D";
            		numDef++;
            	}
            	line = br.readLine();
            	
            	String[] fencerInfo = line.split("	");
            	
            	fencer = fencerInfo[fencerInfo.length-1];
            	
            	line = br.readLine();
            	line = br.readLine();
            	line = br.readLine();
            	
            	String[] oppInfo = line.split("	");
            	//opponent = line;
            	opponent = oppInfo[oppInfo.length-1];
            	
            	
            	line = br.readLine();
            	
            	line = br.readLine();
            	
            	line = br.readLine();
            	
            	line = br.readLine();
            	
            	line = br.readLine();
            
            	
            	
            	String[] tsInfo = line.split("	");
            	ts = Integer.parseInt(tsInfo[tsInfo.length-1]);
            	tstot = tstot + ts;
            	line = br.readLine();
            	line = br.readLine();
            	line = br.readLine();
            	
            	
            	String[] trInfo = line.split("	");
            	tr = Integer.parseInt(trInfo[trInfo.length-1]);
            	trtot = trtot + tr;
            	
            	String line2;
            	
            	//check which file to open
            	String season = null;
            	String s = null;
            	
            	if(date.contains("05-09-") || date.contains("05-10-") || date.contains("05-11-") || date.contains("05-12-") || date.contains("06-01-") || date.contains("06-02-") || date.contains("06-03-") || date.contains("06-04-") || date.contains("06-05-") || date.contains("06-06-") || date.contains("06-07") || date.contains("06-08")){
            		season = "2005-2006";
            		s = "2006";
            	}
            	if(date.contains("06-09-") || date.contains("06-10-") || date.contains("06-11-") || date.contains("06-12-") || date.contains("07-01-") || date.contains("07-02-") || date.contains("07-03-") || date.contains("07-04-") || date.contains("07-05-") || date.contains("07-06-")|| date.contains("07-07-") || date.contains("07-08")){
            		season = "2006-2007";
            		s= "2007";
            		
            	}
            	if(date.contains("07-09-") || date.contains("07-10-") || date.contains("07-11-") || date.contains("07-12-") || date.contains("08-01-") || date.contains("08-02-") || date.contains("08-03-") || date.contains("08-04-") || date.contains("08-05-") || date.contains("08-06-")|| date.contains("08-07-") || date.contains("08-08")){
            		season = "2007-2008";
            		s = "2008";
            	}
            	if(date.contains("08-09-") || date.contains("08-10-") || date.contains("08-11-") || date.contains("08-12-") || date.contains("09-01-") || date.contains("09-02-") || date.contains("09-03-") || date.contains("09-04-") || date.contains("09-05-") || date.contains("09-06-") || date.contains("09-07-") || date.contains("09-08")){
            		season = "2008-2009";
            		s = "2009";
            	}
            	if(date.contains("09-09-") || date.contains("09-10-") || date.contains("09-11-") || date.contains("09-12-") || date.contains("10-01-") || date.contains("10-02-") || date.contains("10-03-") || date.contains("10-04-") || date.contains("10-05-") || date.contains("10-06-") || date.contains("10-07-") || date.contains("10-08")){
            		season = "2009-2010";
            		s = "2010";
            	}
            	if(date.contains("10-09-") || date.contains("10-10-") || date.contains("10-11-") || date.contains("10-12-") || date.contains("11-01-") || date.contains("11-02-") || date.contains("11-03-") || date.contains("11-04-") || date.contains("11-05-") || date.contains("11-06-") || date.contains("11-07-") || date.contains("11-08")){
            		season = "2010-2011";
            		s = "2011";
            	}
            	if(date.contains("11-09-") || date.contains("11-10-") || date.contains("11-11-") || date.contains("11-12-") || date.contains("12-01-") || date.contains("12-02-") || date.contains("12-03-") || date.contains("12-04-") || date.contains("12-05-") || date.contains("12-06-") || date.contains("12-07-") || date.contains("12-08")){
            		season = "2011-2012";
            		s="2012";
            	}
            	if(date.contains("12-09-") || date.contains("12-10-") || date.contains("12-11-") || date.contains("12-12-") || date.contains("13-01-") || date.contains("13-02-") || date.contains("13-03-") || date.contains("13-04-") || date.contains("13-05-") || date.contains("13-06-") || date.contains("13-07-") || date.contains("13-08")){
            		season = "2012-2013";
            		s="2013";
            	}
            	if(date.contains("13-09-") || date.contains("13-10-") || date.contains("13-11-") || date.contains("13-12-") || date.contains("14-01-") || date.contains("14-02-") || date.contains("14-03-") || date.contains("14-04-") || date.contains("14-05-") || date.contains("14-06-") || date.contains("14-07-") || date.contains("14-08")){
            		season = "2013-2014";
            		s="2014";
            	}
            	if(date.contains("14-09-") || date.contains("14-10-") || date.contains("14-11-") || date.contains("14-12-") || date.contains("15-01-") || date.contains("15-02-") || date.contains("15-03-") || date.contains("15-04-") || date.contains("15-05-") || date.contains("15-06-") || date.contains("15-07-") || date.contains("15-08")){
            		season = "2014-2015";
            		s="2015";
            	}
            	if(date.contains("15-09-") || date.contains("15-10-") || date.contains("15-11-") || date.contains("15-12-") || date.contains("16-01-") || date.contains("16-02-") || date.contains("16-03-") || date.contains("16-04-") || date.contains("16-05-") || date.contains("16-06-") || date.contains("16-07-") || date.contains("16-08")){
            		season = "2015-2016";
            		s="2016";
            	}
            	if(date.contains("16-09-") || date.contains("16-10-") || date.contains("16-11-") || date.contains("16-12-") || date.contains("17-01-") || date.contains("17-02-") || date.contains("17-03-") || date.contains("17-04-") || date.contains("17-05-") || date.contains("17-06-") || date.contains("17-07-") || date.contains("17-08")){
            		season = "2016-2017";
            		s="2017";
            	}
            	if(date.contains("17-09-") || date.contains("17-10-") || date.contains("17-11-") || date.contains("17-12-") || date.contains("18-01-") || date.contains("18-02-") || date.contains("18-03-") || date.contains("18-04-") || date.contains("18-05-") || date.contains("18-06-") || date.contains("18-07-") || date.contains("18-08")){
            		season = "2017-2018";
            		s="2018";
            	}
            	if(date.contains("18-09-") || date.contains("18-10-") || date.contains("18-11-") || date.contains("18-12-") || date.contains("19-01-") || date.contains("19-02-") || date.contains("19-03-") || date.contains("19-04-") || date.contains("19-05-") || date.contains("19-06-") || date.contains("19-07-") || date.contains("19-08")){
            		season = "2018-2019";
            		s="2019";
            	}
            	
            	URL urlr;
    			urlr = new URL("http://fie.org/results-statistic/ranking/pdf?category=" + category+  "&weapon=" + weapon+ "&gender=F&event=I&season=" + s+ "&federation=&name=");
    			InputStream isr = urlr.openStream();
    			try (BufferedReader bufr = new BufferedReader(new InputStreamReader(isr))){
            	//http://fie.org/results-statistic/ranking/pdf?category=S&weapon=F&gender=F&event=I&season=2019&federation=&name=
            		//try (BufferedReader bufr = new BufferedReader(new FileReader("src/"+weapon + "F" + category + season + ".txt"))) {
    				String rank = null;	
    				while ((line2 = bufr.readLine()) != null) {
            				//System.out.println(line2);
            				
            				if(line2.contains("td class=") && line2.contains("col1")){
            					rank = line2.substring(20);
            					rank = rank.substring(0,rank.length()-5);
            				}
            				if(line2.contains(opponent)){
            					line2 = bufr.readLine();
            					ranking = rank;
            					country = line2.substring(20,23);
            				}
            			}	
            		}
            	
            			System.out.println(fencer + "," + birthday + "," + age + "," + vd + "," + ts + "," + tr + "," + opponent + "," + country + "," + ranking + "," + weapon +",F," + category + "," + city + "," + type + "," + date);
            			sb.append(fencer);
                    	sb.append(";");
                    	sb.append(birthday);
                    	sb.append(";");
                    	sb.append(age);
                    	sb.append(";");
                    	sb.append(vd);
                    	sb.append(";");
                    	sb.append(ts);
                    	sb.append(";");
                    	sb.append(tr);
                    	sb.append(";");
                    	sb.append(opponent);
                    	sb.append(";");
                    	sb.append(country);
                    	sb.append(";");
                    	sb.append(ranking);
                    	sb.append(";");
                    	sb.append("");
                    	sb.append(";");
                    	sb.append(weapon);
                    	sb.append(";");
                    	sb.append("F");
                    	sb.append(";");
                    	sb.append(category);
                    	sb.append(";");
                    	sb.append(city);
                    	sb.append(";");
                    	sb.append(type);
                    	sb.append(";");
                    	sb.append(date);
                    	sb.append('\n');
            }
            
           // System.out.println();
            
            	
            } 
            //write the data file to csv 
            pw.write(sb.toString());    
            pw.close();
            System.out.println("done!");
            
            
            
	        StringBuilder sb2 = new StringBuilder();
	        sb2.append("----- STATISTICS -----");
	        sb2.append('\n');
            //System.out.println("----- STATISTICS -----");
            sb2.append("FENCER NAME: " + fencer);
            sb2.append('\n');
            sb2.append("DATE OF BIRTH: "+ birthday);
            sb2.append('\n');
            age = 2019 - Integer.parseInt(birthday.substring(0,4));
        	if(01 < Integer.parseInt(birthday.substring(5,7))){
        		age--;
        	}
        	else if(01 == Integer.parseInt(birthday.substring(5,7))){
        		
        		if(22 < Integer.parseInt(birthday.substring(8,10))){
        			age--;
        		}		
        	}
        	
            sb2.append("AGE: " + age);
            sb2.append('\n');
            
           
            
            String line2;
            String season = "2018-2019";
            int rank;
            try (BufferedReader bufr = new BufferedReader(new FileReader("src/" + "F" + "F" + "S" + season + ".txt"))) {
    			while ((line2 = bufr.readLine()) != null) {
    				if(line2.contains(fencer)){
    					String[] advInfo = line2.split("	");
    					rank = Integer.parseInt(advInfo[0]);
    				}
    			}
            }
            //general statistics
            sb2.append("RANKING: " + ranking);
            sb2.append('\n');
            sb2.append("NUMBER OF VICTORIES: " + numVict);
            sb2.append('\n');
            int numofmatches = numVict + numDef;
            int perc = numVict * 100 / numofmatches;
            sb2.append("PERCENTAGE OF VICTORIES: " + perc  + "%");
            sb2.append('\n');
            sb2.append("NUMBER OF TOUCHES SCORED: " + tstot);
            sb2.append('\n');
          	sb2.append("NUMBER OF TOUCHES RECEIVED: " + trtot);
          	sb2.append('\n');
            sb2.append("FIRST RESULT FROM AGE: " + agemin);
            sb2.append('\n');
            sb2.append("----- PRT STATISTICS -----");
            sb2.append('\n');
           
            //For cadet matches
            for(int i = agemin; i <= 16; i++){
            	//change the file name
            	try (BufferedReader br2 = new BufferedReader(new InputStreamReader(
                        new FileInputStream("MatchDataGOLDIE.csv"), StandardCharsets.UTF_8));) {
                line2 = br2.readLine();
                int temp = 0, vict = 0, top5 = 0, vtop5 = 0, top16 = 0, vtop16 = 0, top32=0, vtop32 = 0, top64 = 0, vtop64 = 0, top100 = 0, vtop100 = 0, top200 = 0, vtop200 = 0, top500 = 0, vtop500 = 0;
                while ((line2 = br2.readLine()) != null) {
                	if(line2.contains("BRIND&#039") || line2.contains("D&#039")){
                		line2 = br2.readLine();
                	}
                	String[] matchInfo = line2.split(";");
                	if(Integer.parseInt(matchInfo[2]) == i && matchInfo[12].equals("C")){
                		temp++;
                		if(matchInfo[3].equals("V")){
                			vict++;
                		}
                	}
                	//there was a problem here with matchInfo[8] when names had weird things like Pam's name (BRIND&#039)
                	if(Integer.parseInt(matchInfo[2]) == i && Integer.parseInt(matchInfo[8]) <= 5 && matchInfo[12].equals("C")){
                		top5++;
                		if(matchInfo[3].equals("V")){
                			vtop5++;
                		}
                	}
                	if(Integer.parseInt(matchInfo[2]) == i && Integer.parseInt(matchInfo[8]) <= 16 && Integer.parseInt(matchInfo[8]) > 5 && matchInfo[12].equals("C")){
                		top16++;
                		if(matchInfo[3].equals("V")){
                			vtop16++;
                		}
                	}
                	if(Integer.parseInt(matchInfo[2]) == i && Integer.parseInt(matchInfo[8]) <= 32 && Integer.parseInt(matchInfo[8]) > 16 && matchInfo[12].equals("C")){
                		top32++;
                		if(matchInfo[3].equals("V")){
                			vtop32++;
                		}
                	}
                	if(Integer.parseInt(matchInfo[2]) == i && Integer.parseInt(matchInfo[8]) <= 64 && Integer.parseInt(matchInfo[8]) > 32 && matchInfo[12].equals("C")){
                		top64++;
                		if(matchInfo[3].equals("V")){
                			vtop64++;
                		}
                	}
                	if(Integer.parseInt(matchInfo[2]) == i && Integer.parseInt(matchInfo[8]) <= 100 && Integer.parseInt(matchInfo[8]) > 64 && matchInfo[12].equals("C")){
                		top100++;
                		if(matchInfo[3].equals("V")){
                			vtop100++;
                		}
                	}
                	if(Integer.parseInt(matchInfo[2]) == i && Integer.parseInt(matchInfo[8]) <= 200 && Integer.parseInt(matchInfo[8]) > 100 && matchInfo[12].equals("C")){
                		top200++;
                		if(matchInfo[3].equals("V")){
                			vtop200++;
                		}
                	}
                	if(Integer.parseInt(matchInfo[2]) == i && Integer.parseInt(matchInfo[8]) <= 500 && Integer.parseInt(matchInfo[8]) > 100 && matchInfo[12].equals("C")){
                		top500++;
                		if(matchInfo[3].equals("V")){
                			vtop500++;
                		}
                	}
                }
                sb2.append("AT AGE " + i + ", " + fencer + " fenced " + temp +  " cadet matches.");
                sb2.append('\n');
            	try
            	{	
            		sb2.append(vict + " of those were victories. That's a victory percentage of " + vict * 100/temp  + "%");
            		sb2.append('\n');
            	}catch (ArithmeticException ae) {
            		sb2.append("They fenced 0 cadet matches found this year.");
            		sb2.append('\n');
                }
            	try{
            		sb2.append(top5 + " of those matches were against top 5, and they won " + vtop5 + " times. That's a win percentage of " + vtop5 * 100 / top5 + "%");
            		sb2.append('\n');
            	}catch (ArithmeticException ae) {
            		sb2.append("0 of those matches were against top 5.");
                    sb2.append('\n');
                }
            	try
            	{
            		sb2.append(top16 + " of those matches were against top 16, and they won " + vtop16 + " times. That's a win percentage of " + vtop16 * 100 / top16 + "%");
            		sb2.append('\n');
            	}catch (ArithmeticException ae) {
            		sb2.append("0 of those matches were against top 16.");
                    sb2.append('\n');
            	}
            	try
            	{
            		sb2.append(top32 + " of those matches were against top 32, and they won " + vtop32 + " times. That's a win percentage of " + vtop32 * 100 / top32 + "%");
            		sb2.append('\n');
            	}catch (ArithmeticException ae) {
                    sb2.append("0 of those matches were against top 32.");
                    sb2.append('\n');
            	}
            	try
            	{
            		sb2.append(top64 + " of those matches were against top 64, and they won " + vtop64 + " times. That's a win percentage of " + vtop64 * 100 / top64 + "%");
            		sb2.append('\n');
            	}catch (ArithmeticException ae) {
                    sb2.append("0 of those matches were against top 64.");
                    sb2.append('\n');
                }
            	try{
            		sb2.append(top100 + " of those matches were against top 100, and they won " + vtop100 + " times. That's a win percentage of " + vtop100 * 100 / top100 + "%");
            		sb2.append('\n');
            	}catch (ArithmeticException ae) {
            		sb2.append("0 of those matches were against top 100.");
            		sb2.append('\n');
                }
            	try{
            		sb2.append(top200 + " of those matches were against top 200, and they won " + vtop200 + " times. That's a win percentage of " + vtop200 * 100 / top200 + "%");
            		sb2.append('\n');
            	}catch (ArithmeticException ae) {
            		sb2.append("A0 of those matches were against top 200.");
            		sb2.append('\n');
            	}
            	try{
            		sb2.append(top500 + " of those matches were against top 500, and they won " + vtop500 + " times. That's a win percentage of " + vtop500 * 100 / top500 + "%");
            		sb2.append('\n');
            	}catch (ArithmeticException ae) {
            		sb2.append("0 of those matches were against top 500");
            		sb2.append('\n');
            	}
            	}
            }
            	
            
            //for junior matches
            for(int i = agemin; i <= 20; i++){
            	//change the file name
            	try (BufferedReader br2 = new BufferedReader(new InputStreamReader(
                        new FileInputStream("MatchDataGOLDIE.csv"), StandardCharsets.UTF_8));) {
                line2 = br2.readLine();
                int temp = 0, vict = 0, top5 = 0, vtop5 = 0, top16 = 0, vtop16 = 0, top32=0, vtop32 = 0, top64 = 0, vtop64 = 0, top100 = 0, vtop100 = 0, top200 = 0, vtop200 = 0, top500 = 0, vtop500 = 0;
                while ((line2 = br2.readLine()) != null) {
                	//System.out.println(line2);
                	if(line2.contains("BRIND&#039") || line2.contains("D&#039") || line2.contains("NOVALIN&#039")){
                		line2 = br2.readLine();
                	}
                	String[] matchInfo = line2.split(";");
                	if(Integer.parseInt(matchInfo[2]) == i && matchInfo[12].equals("J")){
                		temp++;
                		if(matchInfo[3].equals("V")){
                			vict++;
                		}
                	}
                	
                	if(Integer.parseInt(matchInfo[2]) == i && Integer.parseInt(matchInfo[8]) <= 5 && matchInfo[12].equals("J")){
                		top5++;
                		if(matchInfo[3].equals("V")){
                			vtop5++;
                		}
                	}
                	if(Integer.parseInt(matchInfo[2]) == i && Integer.parseInt(matchInfo[8]) <= 16 && Integer.parseInt(matchInfo[8]) > 5 && matchInfo[12].equals("J")){
                		top16++;
                		if(matchInfo[3].equals("V")){
                			vtop16++;
                		}
                	}
                	if(Integer.parseInt(matchInfo[2]) == i && Integer.parseInt(matchInfo[8]) <= 32 && Integer.parseInt(matchInfo[8]) > 16 && matchInfo[12].equals("J")){
                		top32++;
                		if(matchInfo[3].equals("V")){
                			vtop32++;
                		}
                	}
                	if(Integer.parseInt(matchInfo[2]) == i && Integer.parseInt(matchInfo[8]) <= 64 && Integer.parseInt(matchInfo[8]) > 32 && matchInfo[12].equals("J")){
                		top64++;
                		if(matchInfo[3].equals("V")){
                			vtop64++;
                		}
                	}
                	if(Integer.parseInt(matchInfo[2]) == i && Integer.parseInt(matchInfo[8]) <= 100 && Integer.parseInt(matchInfo[8]) > 64 && matchInfo[12].equals("J")){
                		top100++;
                		if(matchInfo[3].equals("V")){
                			vtop100++;
                		}
                	}
                	if(Integer.parseInt(matchInfo[2]) == i && Integer.parseInt(matchInfo[8]) <= 200 && Integer.parseInt(matchInfo[8]) > 100 && matchInfo[12].equals("J")){
                		top200++;
                		if(matchInfo[3].equals("V")){
                			vtop200++;
                		}
                	}
                	if(Integer.parseInt(matchInfo[2]) == i && Integer.parseInt(matchInfo[8]) <= 500 && Integer.parseInt(matchInfo[8]) > 100 && matchInfo[12].equals("J")){
                		top500++;
                		if(matchInfo[3].equals("V")){
                			vtop500++;
                		}
                	}
                }
            	
                sb2.append("AT AGE " + i + ", " + fencer + " fenced " + temp +  " junior matches.");
                sb2.append('\n');
            	try
            	{	
            		sb2.append(vict + " of those were victories. That's a victory percentage of " + vict * 100/temp  + "%");
            		sb2.append('\n');
            	}catch (ArithmeticException ae) {
            		sb2.append("ArithmeticException occured!");
            		sb2.append('\n');
                }
            	try{
            		sb2.append(top5 + " of those matches were against top 5, and they won " + vtop5 + " times. That's a win percentage of " + vtop5 * 100 / top5 + "%");
            		sb2.append('\n');
            	}catch (ArithmeticException ae) {
            		sb2.append("0 of those matches were against top 5.");
                    sb2.append('\n');
                }
            	try
            	{
            		sb2.append(top16 + " of those matches were against top 16, and they won " + vtop16 + " times. That's a win percentage of " + vtop16 * 100 / top16 + "%");
            		sb2.append('\n');
            	}catch (ArithmeticException ae) {
            		sb2.append("0 of those matches were against top 16.");
                    sb2.append('\n');
            	}
            	try
            	{
            		sb2.append(top32 + " of those matches were against top 32, and they won " + vtop32 + " times. That's a win percentage of " + vtop32 * 100 / top32 + "%");
            		sb2.append('\n');
            	}catch (ArithmeticException ae) {
                    sb2.append("0 of those matches were against top 32.");
                    sb2.append('\n');
            	}
            	try
            	{
            		sb2.append(top64 + " of those matches were against top 64, and they won " + vtop64 + " times. That's a win percentage of " + vtop64 * 100 / top64 + "%");
            		sb2.append('\n');
            	}catch (ArithmeticException ae) {
                    sb2.append("0 of those matches were against top 64.");
                    sb2.append('\n');
                }
            	try{
            		sb2.append(top100 + " of those matches were against top 100, and they won " + vtop100 + " times. That's a win percentage of " + vtop100 * 100 / top100 + "%");
            		sb2.append('\n');
            	}catch (ArithmeticException ae) {
            		sb2.append("0 of those matches were against top 100.");
            		sb2.append('\n');
                }
            	try{
            		sb2.append(top200 + " of those matches were against top 200, and they won " + vtop200 + " times. That's a win percentage of " + vtop200 * 100 / top200 + "%");
            		sb2.append('\n');
            	}catch (ArithmeticException ae) {
            		sb2.append("0 of those matches were against top 200.");
            		sb2.append('\n');
            	}
            	try{
            		sb2.append(top500 + " of those matches were against top 500, and they won " + vtop500 + " times. That's a win percentage of " + vtop500 * 100 / top500 + "%");
            		sb2.append('\n');
            	}catch (ArithmeticException ae) {
            		sb2.append("0 of those matches were against top 500");
            		sb2.append('\n');
            	}
            }
           
        }
            
            //for senior matches
            for(int i = agemin; i <= agemax; i++){
            	//change the name
            	try (BufferedReader br2 = new BufferedReader(new InputStreamReader(
                        new FileInputStream("MatchDataGOLDIE.csv"), StandardCharsets.UTF_8));) {
                line2 = br2.readLine();
                int temp = 0, vict = 0, top5 = 0, vtop5 = 0, top16 = 0, vtop16 = 0, top32=0, vtop32 = 0, top64 = 0, vtop64 = 0, top100 = 0, vtop100 = 0, top200 = 0, vtop200 = 0, top500 = 0, vtop500 = 0;
                while ((line2 = br2.readLine()) != null) {
                	if(line2.contains("BRIND&#039") || line2.contains("D&#039") || line2.contains("NOVALIN&#039")){
                		line2 = br2.readLine();
                	}
                	String[] matchInfo = line2.split(";");
                	//System.out.println(matchInfo[1]);
                	if(Integer.parseInt(matchInfo[2]) == i && matchInfo[12].equals("S")){
                		temp++;
                		if(matchInfo[3].equals("V")){
                			vict++;
                		}
                	}
                	if(Integer.parseInt(matchInfo[2]) == i && Integer.parseInt(matchInfo[8]) <= 5 && matchInfo[12].equals("S")){
                		top5++;
                		if(matchInfo[3].equals("V")){
                			vtop5++;
                		}
                	}
                	if(Integer.parseInt(matchInfo[2]) == i && Integer.parseInt(matchInfo[8]) <= 16 && Integer.parseInt(matchInfo[8]) > 5 && matchInfo[12].equals("S")){
                		top16++;
                		if(matchInfo[3].equals("V")){
                			vtop16++;
                		}
                	}
                	if(Integer.parseInt(matchInfo[2]) == i && Integer.parseInt(matchInfo[8]) <= 32 && Integer.parseInt(matchInfo[8]) > 16 && matchInfo[12].equals("S")){
                		top32++;
                		if(matchInfo[3].equals("V")){
                			vtop32++;
                		}
                	}
                	if(Integer.parseInt(matchInfo[2]) == i && Integer.parseInt(matchInfo[8]) <= 64 && Integer.parseInt(matchInfo[8]) > 32 && matchInfo[12].equals("S")){
                		top64++;
                		if(matchInfo[3].equals("V")){
                			vtop64++;
                		}
                	}
                	if(Integer.parseInt(matchInfo[2]) == i && Integer.parseInt(matchInfo[8]) <= 100 && Integer.parseInt(matchInfo[8]) > 64 && matchInfo[12].equals("S")){
                		top100++;
                		if(matchInfo[3].equals("V")){
                			vtop100++;
                		}
                	}
                	if(Integer.parseInt(matchInfo[2]) == i && Integer.parseInt(matchInfo[8]) <= 200 && Integer.parseInt(matchInfo[8]) > 100 && matchInfo[12].equals("S")){
                		top200++;
                		if(matchInfo[3].equals("V")){
                			vtop200++;
                		}
                	}
                	if(Integer.parseInt(matchInfo[2]) == i && Integer.parseInt(matchInfo[8]) <= 500 && Integer.parseInt(matchInfo[8]) > 100 && matchInfo[12].equals("S")){
                		top500++;
                		if(matchInfo[3].equals("V")){
                			vtop500++;
                		}
                	}
                }
            	
                sb2.append("AT AGE " + i + ", " + fencer + " fenced " + temp +  " senior matches.");
                sb2.append('\n');  
                try
            	{	
            		sb2.append(vict + " of those were victories. That's a victory percentage of " + vict * 100/temp  + "%");
            		sb2.append('\n');
            	}catch (ArithmeticException ae) {
            		sb2.append("ArithmeticException occured!");
            		sb2.append('\n');
                }
            	try{
            		sb2.append(top5 + " of those matches were against top 5, and they won " + vtop5 + " times. That's a win percentage of " + vtop5 * 100 / top5 + "%");
            		sb2.append('\n');
            	}catch (ArithmeticException ae) {
            		sb2.append("0 of those matches were against top 5.");
                    sb2.append('\n');
                }
            	try
            	{
            		sb2.append(top16 + " of those matches were against top 16, and they won " + vtop16 + " times. That's a win percentage of " + vtop16 * 100 / top16 + "%");
            		sb2.append('\n');
            	}catch (ArithmeticException ae) {
            		sb2.append("0 of those matches were against top 16.");
                    sb2.append('\n');
            	}
            	try
            	{
            		sb2.append(top32 + " of those matches were against top 32, and they won " + vtop32 + " times. That's a win percentage of " + vtop32 * 100 / top32 + "%");
            		sb2.append('\n');
            	}catch (ArithmeticException ae) {
                    sb2.append("0 of those matches were against top 32.");
                    sb2.append('\n');
            	}
            	try
            	{
            		sb2.append(top64 + " of those matches were against top 64, and they won " + vtop64 + " times. That's a win percentage of " + vtop64 * 100 / top64 + "%");
            		sb2.append('\n');
            	}catch (ArithmeticException ae) {
                    sb2.append("0 of those matches were against top 64.");
                    sb2.append('\n');
                }
            	try{
            		sb2.append(top100 + " of those matches were against top 100, and they won " + vtop100 + " times. That's a win percentage of " + vtop100 * 100 / top100 + "%");
            		sb2.append('\n');
            	}catch (ArithmeticException ae) {
            		sb2.append("0 of those matches were against top 100.");
            		sb2.append('\n');
                }
            	try{
            		sb2.append(top200 + " of those matches were against top 200, and they won " + vtop200 + " times. That's a win percentage of " + vtop200 * 100 / top200 + "%");
            		sb2.append('\n');
            	}catch (ArithmeticException ae) {
            		sb2.append("0 of those matches were against top 200.");
            		sb2.append('\n');
            	}
            	try{
            		sb2.append(top500 + " of those matches were against top 500, and they won " + vtop500 + " times. That's a win percentage of " + vtop500 * 100 / top500 + "%");
            		sb2.append('\n');
            	}catch (ArithmeticException ae) {
            		sb2.append("0 of those matches were against top 500");
            		sb2.append('\n');
            	}
            }
           
        }
            pw2.write(sb2.toString());    
            pw2.close();
            System.out.println("done!");
           

        }
	}

	public static String[] getURL() throws IOException {
		URL url;
		url = new URL("http://fie.org/results-statistic/ranking?result_models_Ranks%5BFencCatId%5D=S&result_models_Ranks%5BWeaponId%5D=F&result_models_Ranks%5BGenderId%5D=F&result_models_Ranks%5BCompTypeId%5D=I&result_models_Ranks%5BCPYear%5D=2019&result_models_Ranks%5BNationality%5D=&result_models_Ranks%5BLastName%5D=");
		InputStream is = url.openStream();
		String line;
		String[] urls = new String[30];
		int i= 0;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))){
			while((line = br.readLine()) != null){
				if(line.contains("</td><td><a href=")){
					//System.out.println(line);
					String[] urlinfo = line.split("/");
					urls[i] = urlinfo[14];
					i++;
					//System.out.println(urlinfo[14]);
					
				}
			}
		}
		return urls;
		
		
	}
}
