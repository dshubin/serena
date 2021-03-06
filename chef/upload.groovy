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
//required
final def pattern = props['uploadCookbookPattern'];
final def workingDir = props['workingDir'];
def sout = new StringBuffer();
def serr = new StringBuffer();

//Optional
def uploadCookbookCon = "";
def uploadCookbookDi = "";
def uploadCookbookFo = "";
def uploadCookbookFr = "";
def uploadCookbookDr = "";
def uploadCookbookPu = "";
def uploadCookbookRec = "";
def uploadCookbookRepo = "";

if(props['uploadConcurrency'] != "")
	uploadCookbookCon = "--concurrency " + props['uploadConcurrency'];
	
if(props['uploadDiff'] != "true")
	uploadCookbookDi = "--no-diff";
	
if(props['uploadForce'] != "false")
	uploadCookbookFo = "--force";

if(props['uploadFreeze'] != "false")
	uploadCookbookFr = "--freeze";

if(props['uploadDryRun'] != "false")
	uploadCookbookDr = "-n";
	
if(props['uploadPurge'] != "false")
	uploadCookbookPu = "--purge";
	
if(props['uploadRecurse'] != "false")
	uploadCookbookRec = "--no-recurse";

if(props['uploadRepoMode'] != "")
	uploadCookbookRepo = "--repo-mode " + props['uploadRepoMode'];
	
final def options = "${uploadCookbookCon} ${uploadCookbookDi} ${uploadCookbookFo} ${uploadCookbookFr} ${uploadCookbookDr} ${uploadCookbookPu} ${uploadCookbookRec} ${uploadCookbookRepo}";
final def command = "knife upload ${pattern} ${options}";

def proc = command.execute(null, new File(workingDir));
proc.consumeProcessOutput(sout, serr);
proc.waitFor();

println "sout: ${sout}";
println "serr: ${serr}";

System.exit(proc.exitValue());
