module pl.cp.model {
    requires org.apache.commons.lang3;
    requires java.sql;
    requires slf4j.api;

    exports pl.cp.model;
    exports pl.cp.model.dao;
    exports pl.cp.model.parts;
    exports pl.cp.model.solver;
}