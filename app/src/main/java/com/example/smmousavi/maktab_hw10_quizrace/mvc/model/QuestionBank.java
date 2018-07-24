package com.example.smmousavi.maktab_hw10_quizrace.mvc.model;

import android.content.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class QuestionBank {
    private static QuestionBank instance;
    Context mctx;

    public static QuestionBank getInstance(Context context) {
        if (instance == null)
            instance = new QuestionBank(context);

        return instance;
    }

    private QuestionBank(Context context) {
        mctx = context;
    }

    public static final class Science {

        public static final String text = "Science";

        public static final class Easy {

            public static final String text = "Easy";

            public static final class Q1 {
                public static final String text = "How many lungs does the human body have?";
                public static final List<String> answersText = new ArrayList<>();
                public static int trueNumber = 2;

                static {
                    answersText.add("One");
                    answersText.add("Two");
                    answersText.add("Three");
                    answersText.add("Four");
                }
            }

            public static final class Q2 {
                public static final String text = "What is the human body’s biggest organ?";
                public static final List<String> answersText = new ArrayList<>();
                public int trueNumber = 2;

                static {
                    answersText.add("Skin");
                    answersText.add("Liver");
                    answersText.add("Heart");
                    answersText.add("Brain");
                }
            }
        }

        public static final class Moderate {


            public static final class Q1 {
                public static final String text = "What is the centre of an atom called?";
                public static final List<String> answersText = new ArrayList<>();
                public int trueNumber = 4;

                static {
                    answersText.add("Proton");
                    answersText.add("Electron");
                    answersText.add("Neutron");
                    answersText.add("Nucleus");
                }
            }

            public static final class Q2 {
                public static final String text = "What is the main gas found in the air we breathe?";
                public static final List<String> answersText = new ArrayList<>();
                public int trueNumber = 4;

                static {
                    answersText.add("Proton");
                    answersText.add("Electron");
                    answersText.add("Neutron");
                    answersText.add("Nucleus");
                }
            }

        }

        public static final class Tough {
            public static final class Q1 {
                public static final String text = "What is the centre of an atom called?";
                public static final List<String> answersText = new ArrayList<>();
                public int trueNumber = 4;

                static {
                    answersText.add("Proton");
                    answersText.add("Electron");
                    answersText.add("Neutron");
                    answersText.add("Nucleus");
                }
            }

        }
    }

    public static final class Sport {

        public static final class Easy {
            public static final class Q1 {
                public static final String text = "What is the centre of an atom called?";
                public static final List<String> answersText = new ArrayList<>();
                public int trueNumber = 4;

                static {
                    answersText.add("Proton");
                    answersText.add("Electron");
                    answersText.add("Neutron");
                    answersText.add("Nucleus");
                }
            }

            public static final class Q2 {
                public static final String text = "What is the centre of an atom called?";
                public static final List<String> answersText = new ArrayList<>();
                public int trueNumber = 4;

                static {
                    answersText.add("Proton");
                    answersText.add("Electron");
                    answersText.add("Neutron");
                    answersText.add("Nucleus");
                }
            }
        }

        public static final class Moderate {
            public static final class Q1 {
                public static final String text = "What is the centre of an atom called?";
                public static final List<String> answersText = new ArrayList<>();
                public int trueNumber = 4;

                static {
                    answersText.add("Proton");
                    answersText.add("Electron");
                    answersText.add("Neutron");
                    answersText.add("Nucleus");
                }
            }

            public static final class Q2 {
                public static final String text = "What is the centre of an atom called?";
                public static final List<String> answersText = new ArrayList<>();
                public int trueNumber = 4;

                static {
                    answersText.add("Proton");
                    answersText.add("Electron");
                    answersText.add("Neutron");
                    answersText.add("Nucleus");
                }
            }

        }

        public static final class Tough {

            public static final class Q1 {
                public static final String text = "What is the centre of an atom called?";
                public static final List<String> answersText = new ArrayList<>();
                public int trueNumber = 4;

                static {
                    answersText.add("Proton");
                    answersText.add("Electron");
                    answersText.add("Neutron");
                    answersText.add("Nucleus");
                }
            }
        }

    }

    public static final class Technology {

        public static final class Easy {
            public static final class Q1 {
                public static final String text = "What is the centre of an atom called?";
                public static final List<String> answersText = new ArrayList<>();
                public int trueNumber = 4;

                static {
                    answersText.add("Proton");
                    answersText.add("Electron");
                    answersText.add("Neutron");
                    answersText.add("Nucleus");
                }
            }

            public static final class Q2 {
                public static final String text = "What is the centre of an atom called?";
                public static final List<String> answersText = new ArrayList<>();
                public int trueNumber = 4;

                static {
                    answersText.add("Proton");
                    answersText.add("Electron");
                    answersText.add("Neutron");
                    answersText.add("Nucleus");
                }
            }
        }

        public static final class Moderate {
            public static final class Q1 {
                public static final String text = "What is the centre of an atom called?";
                public static final List<String> answersText = new ArrayList<>();
                public int trueNumber = 4;

                static {
                    answersText.add("Proton");
                    answersText.add("Electron");
                    answersText.add("Neutron");
                    answersText.add("Nucleus");
                }
            }

            public static final class Q2 {
                public static final String text = "What is the centre of an atom called?";
                public static final List<String> answersText = new ArrayList<>();
                public int trueNumber = 4;

                static {
                    answersText.add("Proton");
                    answersText.add("Electron");
                    answersText.add("Neutron");
                    answersText.add("Nucleus");
                }
            }

        }

        public static final class Tough {

            public static final class Q1 {
                public static final String text = "What is the centre of an atom called?";
                public static final List<String> answersText = new ArrayList<>();
                public int trueNumber = 4;

                static {
                    answersText.add("Proton");
                    answersText.add("Electron");
                    answersText.add("Neutron");
                    answersText.add("Nucleus");
                }
            }
        }

    }

}
