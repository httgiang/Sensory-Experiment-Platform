package main.sensoryexperimentplatform.models;

import java.util.ArrayList;
import java.util.Scanner;
public class Data {
    private ArrayList<Experiment> news;

    /*public Data() throws Exception {
        Scanner sc = new Scanner(System.in);
        //news = DataAccess.importExperiment();

        boolean End = false;
        System.out.println("-----------------------Open------------------------");
        while (!End) {
            System.out.println("Choose your choice: New Experiment(n), Edit Experiment(e), Results (r), Stop(s):");
            String choice = sc.nextLine();
            String titleEn, contentEn;
            switch (choice) {
                case "n":
                    newEx();
                    break;
                case "e":
                    int count = 1;
                    for (Experiment newex : news) {
                        System.out.print(count + ". Creator name: " + newex.getCreatorName() + "\n  Experiment Name: " + newex.getExperimentName() + "\n");
                        count++;
                    }
                    System.out.println("Choose the experiment you want to edit:");
                    count = sc.nextInt() - 1;
                    System.out.println("Choose the stage you want to add:");
                    int stage = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter the title: ");
                    titleEn = sc.nextLine();
                    System.out.print("Enter the content: ");
                    contentEn = sc.nextLine();
                    switch (stage) {
                        case 1:
                            news.get(count).addNotice(titleEn, contentEn);
                            break;
                        case 2:
                            news.get(count).addInput(titleEn, contentEn);
                            break;
                        case 3:
                            news.get(count).addTimer(titleEn, contentEn);
                            break;
                        case 4:
                            news.get(count).addSelectedVAS(titleEn, contentEn);
                            break;
                        case 5:
                            news.get(count).addgLMS(titleEn, contentEn);
                            break;
                        case 6:
                            news.get(count).addQues(titleEn, contentEn);
                            break;
                        default:
                            break;
                    }
                    break;
                case "r":
                    for (Experiment newex : news) {
                        newex.showStages();
                    }
                    break;
                case "s":
                    DataAccess.saveData(news);
                    End = true;
                    System.out.println("-----------------------Close------------------------");
                    break;
                default:
                    break;
            }
        }
    }*/

    public void newEx() {
        Experiment newex = new Experiment();
        news.add(newex);
    }
}
