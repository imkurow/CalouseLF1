module CalouseLF {
	  	requires javafx.controls;
	    requires javafx.graphics;
	    requires java.sql;
	    
	    opens main to javafx.graphics;
	    opens view to javafx.graphics;
}