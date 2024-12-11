module CalouseLF {
	  	requires javafx.controls;
	    requires javafx.graphics;
	    requires java.sql;
		requires javafx.base;
	    
	    opens main to javafx.graphics;
	    opens view to javafx.graphics;
}