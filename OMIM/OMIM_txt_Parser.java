package OMIM;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;



public class OMIM_txt_Parser {
	
public String symptome;
public String dbPath;
public String id;
public LinkedList<OMIM_txt> list;

    public String getSymptome() {
	return symptome;
}
public void setSymptome(String symptome) {
	this.symptome = symptome;
}
public String getDbPath() {
	return dbPath;
}
public void setDbPath(String dbPath) {
	this.dbPath = dbPath;
}
public LinkedList<OMIM_txt> getList() {
	return list;
}
public void setList(LinkedList<OMIM_txt> list) {
	this.list = list;
}
	public OMIM_txt_Parser(){}
    public OMIM_txt_Parser(String symptome,String id) throws IOException{
    	this.symptome=symptome;
    	this.id=id;
    	this.dbPath="/home/depot/2A/gmd/projet_2016-17/omim/omim.txt";
    	final File dbFile = new File(dbPath);
	     if (!dbFile.exists()) {
	       System.out.println("the db file '" +dbPath+ "' does not exist or is not readable, please check the path");
	       System.exit(1);
	     }
	     this.list= OMIM_txt_Parser_remp(dbFile,symptome,id);
    }

	public static LinkedList<OMIM_txt> OMIM_txt_Parser_remp(File file, String sym, String id) throws IOException {
		
		 LinkedList<OMIM_txt> list = new LinkedList<OMIM_txt>();
	     try{    		 
	    	
			FileReader lecteurDeFichier = new FileReader(file);
			BufferedReader br = new BufferedReader(lecteurDeFichier);
			String line;    
			OMIM_txt omim=null;

	    	while((line=br.readLine())!=null){
	    		
	    		
	    		if(line.startsWith("*RECORD*")){
	    			omim= new OMIM_txt();
	    			
	    		}
	    		if (line.startsWith("*FIELD* NO")){
	    			String no="";
	    			no=br.readLine();
	    			omim.setFieldNo(no);
	    			if (no.contains(id) && !list.contains(omim)){
	    				list.add(omim);
	    			}
   			
	    		}
	    		if(line.startsWith("*FIELD* TI")){
	    			String ti="";
	    			ti=br.readLine();
	    			if (ti.startsWith(" "))
	    				ti=ti.substring(7);
	    			else
	    				ti=ti.substring(8);
	    			while(!(line=br.readLine()).contains("*FIELD*")){
	    			
	    				ti+=line;
	    			}
	    			if(ti.contains(";")){
	    				String[] parts = ti.split(";");
	    				ti=parts[0];
	    			}
	    			omim.setFieldTi(ti);
	    		}
	    		if (line.startsWith("DESCRIPTION")){
	    			String description="";
	    			while(!(line=br.readLine()).isEmpty()){
	    				description+=" "+line;
	    				
	    			}
	    			omim.setDescription(description);
	    		}
	    		if(line.startsWith("*FIELD* CS")){
	    			String symptomes="";
	    			while(!(line=br.readLine()).contains("*FIELD*")){
	    				
	    				
	    				if (line.startsWith(" ") && !line.isEmpty() && !line.endsWith("];")){
	    					if (!line.endsWith(";")){
	    						symptomes=line.substring(3);
	    					}
	    					else
	    						symptomes=line.substring(3,line.length()-1);
	    				}	
	    				
	    				if (symptomes.toLowerCase().indexOf(sym.toLowerCase()) >= 0 && !list.contains(omim)){
	    	    			
		    				omim.setSymptomes(symptomes);	
		    				list.add(omim);
		    			}	
	    			
	    			}
	    			
	    			
	    		}
	    	
	    		
	    	}
	    	br.close();
	    	
	     }catch(Exception e){
	    	 e.printStackTrace();
	     }
		return list;
	     
	   }
		


}
