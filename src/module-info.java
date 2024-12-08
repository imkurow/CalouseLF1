module CalouseLF {
	requires java.sql;
	requires javafx.graphics;
	requires javafx.controls;
	opens main;
//	opens models;
//	opens controller;
	opens view;
}