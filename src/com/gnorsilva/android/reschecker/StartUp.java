package com.gnorsilva.android.reschecker;

import static com.gnorsilva.android.reschecker.Utils.log;

public class StartUp {
	
	private static final String ARGS_PATH = "-p";
	
	public static void main(String [] args){
		if(args == null || args.length == 0){
			printHelp();
		}else {
			readArgs(args);
		}

	}

	private static void readArgs(String[] args) {
		for (int i = 0 ; i < args.length ; i++ ){
			String arg = args[i];
			if (arg.equals(ARGS_PATH)){
				if( ++i >= args.length){
					printHelp();
				}else{
					new Controller(args[i]).searchProject();
				}
			}else{
				printHelp();
			}
		}
	}

	public static void printHelp() {
		log();
		log("ResChecker finds out which resources are being used in your Android project");
		log();
		log("Usage:");
		log();
		log("\t-p path of the project to be checked - project needs to have AndroidManifest.xml, and R.java inside the gen folder");
		log("\t-h prints this message");
		log();
	}

}
