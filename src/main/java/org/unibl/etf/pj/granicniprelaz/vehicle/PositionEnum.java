package org.unibl.etf.pj.granicniprelaz.vehicle;

public enum PositionEnum {
        IN_QUEUE("u koloni"),
        AT_POLICE_TERMINAL("na policijskom terminalu"),
        AT_CUSTOMS_TERMINAL("na carini"),
        FINISHED("prešao granicu");

        public final String position;

        PositionEnum(String position) {
            this.position = position;
        }
        @Override
        public String toString() {
            return this.position;
        }

    }
