public class AudioFile{
	String pathname="", author, filename="", title;
	String os = System.getProperty("os.name").toLowerCase();
	char laufwerk;
	
	//Behandlung des Pfadnamens Aufgabe b
	//Aufteilen des Pfad Strings in Attributen
	public void parsePathname(String p){
		pathname = p;
		//Wenn Pfad leer, setze Dateiname auf ""
		if (pathname.isEmpty()){
			filename = "";
			return;
		}
		//Wenn 2te Stelle : dann 1te Stelle Laufwerksbuchstabe
		if(!pathname.equals(" ")&&!pathname.equals("-")&&pathname.charAt(1) == ':') {
			laufwerk = pathname.charAt(0);
		}
		//Ersetzung von mehreren aufeinanderfolgenden / oder \
		pathname=pathname.replace('\\', '/');
		char[] pathArray = pathname.toCharArray();
		int j=1;
		for (int i = 0; i < pathArray.length - j; i++) {
			j = 1;
		    while(pathArray[i]=='/'&&pathArray[i+j]=='/') { 
		    	pathArray[i+j] = '\0'; 
				j++;
				if (i+j>=pathArray.length) {	
					break;
				}
		    }
		}
		pathname = new String(pathArray, 0, pathArray.length);
		pathname = pathname.replace("\0", "");
		//Änderung der Pfadname zu betriebssystemabhängigen Format
		if(os.startsWith("w")){
			pathname = pathname.replace('/','\\');
			if(!pathname.equals(" ")&&pathname.contains("\\")) {
				filename = pathname.substring(pathname.lastIndexOf("\\")+1);
			} else {
				filename = pathname;
			} 
		}
		else{
			if (Character.isLetter(laufwerk)) {
				pathname = pathname.replace(":", "");
				pathname = "/" + pathname;
			}
			if(!pathname.equals(" ")&&pathname.contains("/")) {
				filename = pathname.substring(pathname.lastIndexOf("/")+1);
			} else {
				filename = pathname;
			} 
		}
	}
	//Getter der Pfadname
	public String getPathname(){
		return pathname;
	}
	//Getter der Dateiname
	public String getFilename(){
		return filename;
	}
	//Behandlung des Dateinamens Aufgabe c
	//Aufteilen des Datei Strings in Attributen
	public void parseFilename(String f){
		filename = f;
		//BEHANDLUNG DER SONDERFÄLLE
		//Wenn Pfad leer setze autor und titel auf ""
		if(filename.isEmpty()) {
			author = "";
			title = "";
			return;
		}
		//Wenn filename ist - setze autor auf "" und titel auf -
		if (filename.equals("-")) {
			author = "";
			title = "-";
			return;
		}
		//Wenn filename ist _-_ setze autor und titel auf ""
		if (filename.trim().equals("-")) {
			author = "";
			title = "";
			return;
		}
		//Wenn es kein / oder \\ gibt dann 1.Stelle bis Punkt titel
		if (!pathname.equals(" ")&&!filename.contains("/")&&!filename.contains("\\")&&!filename.contains(" - ")) {
			author = "";
			title = filename.substring(0, filename.indexOf("."));
			return;
		}
		//STANDARDVERHALTEN / Aufteilung der Dateiname in Attributen
		if(!pathname.equals(" ")&&!pathname.endsWith("/")&&filename.contains(" - ")) {
			String[] filepart = filename.split(" - ");
			author = filepart[0].trim();
			title = filepart[1].trim();
			title = title.substring(0, title.lastIndexOf(".")).trim();
		} 
		//SONDERFÄLLE
		if(pathname.equals(" ")||pathname.endsWith("/")){
			author="";
			title="";
		}
	}
	//Getter der Autorenname
	public String getAuthor() {
		return author;
	}
	//Getter der Titelname
	public String getTitle(){
		return title;
	}
	//Konstruktor Aufgabe d
	public AudioFile() {}
	public AudioFile(String input) {
		this.parsePathname(input);
		this.getFilename();
		this.parseFilename(filename);
	}
	//toString() Methode Aufgabe e
	public String toString() {
		if(author.isEmpty()) {
			return title;
		} else
			return author + " - " + title;
	}
	
	public static void main(String[]args){
		AudioFile file1 = new AudioFile("");
		System.out.println(file1.getPathname() + " - " + file1.getFilename());
		System.out.println(file1.getAuthor() + " - " + file1.getTitle());
	}
}