def props = new Properties();
final def inputPropsFile = args[0];
final def inputPropsStream = null;

try {
	inputPropsStream = new FileInputStream(inputPropsFile);
    	props.load(inputPropsStream);
} catch (IOException e) {
	throw new RuntimeException(e);
} finally {
	    inputPropsStream.close();
}

// Required Field
final def filePath = props['filePath'];
def file = new File(filePath);

try {
	file.delete();
} catch (Exception e){
	System.err << "Error in deleting file: ${filePath}\n";
	System.err << e.getMessage();
}

System.exit(proc.exitValue());
