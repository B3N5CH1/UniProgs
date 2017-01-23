<?php
	$log_file = 'sound_log.txt';
	$dbs = $_POST["dBs"];
	
	date_default_timezone_set("UTC");
	
	$str = gmdate("Y M d H:i:s", time()).' '.$dbs."\n";
	
	if (file_exists($log_file)) {
		$fp = fopen($log_file, "a");
		fwrite($fp, $str);
		fclose($fp);
	} else {
		$fp = fopen($log_file, "w");
		fwrite($fp, $str);
		fclose($fp);
	}
?>