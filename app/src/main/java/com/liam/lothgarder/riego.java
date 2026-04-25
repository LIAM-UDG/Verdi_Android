package com.liam.lothgarder;

public class riego {

        private int idPu;
        private String fechaR;
        private int segR;
        private double aguaR;

        public riego(int idPu, String fechaR, int segR, double aguaR) {
            this.idPu = idPu;
            this.fechaR = fechaR;
            this.segR = segR;
            this.aguaR = aguaR;
        }

        public int getIdPu() {
            return idPu;
        }

        public String getFechaR() {
            return fechaR;
        }

        public int getSegR() {
            return segR;
        }

        public double getAguaR() {
            return aguaR;
        }

}
