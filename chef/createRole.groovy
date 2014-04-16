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

final def select = props["select"]
def path = props["filePath"]
final def contents = props["roleContents"]

def sout = new StringBuffer();
def serr = new StringBuffer();

if(select == "textbox")
{
	def file = new File("temp.json").canonicalFile;
	file.write(contents);
	path = "temp.json";
}

def command = "knife role from file ${path}"

def proc = command.execute();
proc.consumeProcessOutput(sout, serr);
proc.waitFor();

if(select == "textbox")
{
	def file = new File("temp.json")
	file.delete();
}


println "sout: ${sout}";
println "serr: ${serr}";

System.exit(proc.exitValue());
