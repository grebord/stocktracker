module javafx {
    requires base;
    requires javafx.controls;
    requires transitive javafx.graphics;
    requires org.tinylog.api;
    exports com.stocktracker.javafx;
}
