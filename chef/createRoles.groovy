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
final def roleName = props['roleName'];
// Optionsal Fields
final def description = props['description'];

def sout = new StringBuffer();
def serr = new StringBuffer();

def command = "knife role create ${roleName}";

if(description){
	command = command + " ${description}";
}

def proc = command.execute();
proc.consumeProcessOutput(sout, serr);
proc.waitFor();

println “sout: ${sout}”;
println “serr: ${serr}”;

System.exit(0);
