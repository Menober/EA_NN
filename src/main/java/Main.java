import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.Keys.ARROW_DOWN;
import static org.openqa.selenium.Keys.SPACE;

public class Main {
    ChromeDriver driver = null;
    int trexXPos = 0;

    Double trexObstacleDistance = 0.0;
    Double obstacleX = 0.0;
    Double obstacleY = 0.0;
    Double isCrashed = 0.0;
    Double speed = 0.0;
    Double type = 0.0;
    Double obstacleHeight = 0.0;

    Double ranDistance = 0.0;

    public static void main(String[] args) throws InterruptedException {
        new Main().main();
    }

    public void main() throws InterruptedException, JavascriptException {
        Scanner scanner = new Scanner(System.in);
        // Optional. If not specified, WebDriver searches the PATH for chromedriver.
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
        driver.get("chrome://dino/");
        Thread.sleep(3000);
        trexXPos = Integer.valueOf(String.valueOf(getTrexXPos()));

        Network network = new Network();
        network.iniNetwork(7, 4, 2);
        network.setConnections();
        network.randomWeights();

        String middleWeights = "-0.7617169844434182;0.20580859876997093;0.25707840726796705;0.9546330858619816;0.8473705660516764;0.46348604391588566;0.17229718773082192;-0.09410260089084987;-0.38462263154754983;0.3662803542666062;-0.2937457847482623;0.6925417620404628;0.3191415296727531;0.4719313897983759;-0.4002817156731191;-0.7081308941039619;0.9125062641529615;0.8540398734260357;-0.5170912431977748;0.5315402807915939;-0.33598294773492143;0.33097353738869595;-0.7012928679035986;-0.5027834791093708;0.18760612077257455;-0.9266803616981933;0.18321390504048507;0.7468151089441553;";
        String outputWeights = "-0.9863638748381285;0.28109806832665685;-0.285803638374752;-0.8774148856724435;-0.1342219507107021;-0.42931503840702745;0.8569676794652559;-0.34255422503070965;";

        Individual myIndiv = new Individual();
        myIndiv.parseToMiddleWeights(middleWeights);
        myIndiv.parseToOutputWeights(outputWeights);

        Population pop = new Population();
        for (int i = 0; i < 10; i++) {
            pop.addIndiv(new Individual(myIndiv));
            myIndiv.mutationMiddle(10000);
            myIndiv.mutationOutput(10000);
        }
//        pop.generateRandomPop(10, network);

        int px = 80000; //100000=100% 10000=10% 1000=1% 100=0.1%
        int pm = 10000;//100000=100% 10000=10% 1000=1% 100=0.1%
        int generations = 100;
        Individual bestEver = new Individual();

       // EA(network, pop, px, pm, generations, bestEver);

        String response = "";
        while (!response.equals("n")) {
            System.out.println("Koniec. Czy chcesz kontynuować? y/n");
            response = scanner.nextLine();
            if (response.equals("y")) {
                System.out.println("Podaj kolejno px, pm, liczbe generacji:");
                px = Integer.parseInt(scanner.nextLine());
                pm = Integer.parseInt(scanner.nextLine());
                generations = Integer.parseInt(scanner.nextLine());
                EA(network, pop, px, pm, generations, bestEver);
            }
        }
        driver.close();
    }

    private void EA(Network network, Population pop, int px, int pm, int generations, Individual bestEver) throws InterruptedException {
        updateVariables();
        System.out.println("START");

        for (int generation = 0; generation < generations; generation++) {
            System.out.println("Generacja: " + generation);
            for (int i = 0; i < pop.population.size(); i++) {
                network.setWeights(pop.population.get(i));
                jump();
                while (isCrashed == 1.0) {
                    jump();
                    Thread.sleep(50);
                    updateVariables();
                }

                while (isCrashed == 0.0) {
                    updateVariables();
                    network.setInputValues(getInputValues());
                    network.calcMiddleLayer();
                    network.calcOutputLayer();
                    if (network.output.get(0).value > 0.5) {
                        jump();
                        Thread.sleep(100);
                    } else if (network.output.get(1).value > 0.5) {
                        dodge();
                        Thread.sleep(100);
                    }
                    //printVariables();
                }
                pop.population.get(i).setFitness(ranDistance);
            }

            System.out.println("Najlepszy w populacji: " + pop.getBest().getFitness());
            System.out.println("Najgorszy w populacji: " + pop.getWorst().getFitness());
            if (bestEver.getFitness() < pop.getBest().getFitness()) {
                bestEver = pop.getBest();
                bestEver.printData();
            }

            pop.crossover(px, 2);
            pop.mutate(pm);
        }

        System.out.println("\nBEST EVER:");
        bestEver.printData();

    }

    private Double[] getInputValues() {
        Double[] values = new Double[7];
        values[0] = trexObstacleDistance;
        values[1] = obstacleX;
        values[2] = obstacleY;
        values[3] = isCrashed;
        values[4] = speed;
        values[5] = type;
        values[6] = obstacleHeight;
        return values;
    }

    public void updateVariables() {
        trexObstacleDistance = getDistance();
        obstacleX = Double.valueOf(String.valueOf(getFirstObstacleXPos()));
        obstacleY = Double.valueOf(String.valueOf(getFirstObstacleYPos()));
        isCrashed = getCrashed() ? 1.0 : 0.0;
        speed = Double.valueOf(String.valueOf(getCurrentSpeed()));
        ranDistance = Double.valueOf(String.valueOf(getDistanceRan()));
        type = getFirstObstacleType();
        obstacleHeight = Double.valueOf(String.valueOf(getFirstObstacleHeight()));
    }

    public void printVariables() {
        System.out.println();
        System.out.println("Trex <-> obst: " + trexObstacleDistance);
        System.out.println("Ob. x: " + obstacleX);
        System.out.println("Ob. y: " + obstacleY);
        System.out.println("Crashed: " + isCrashed);
        System.out.println("Speed: " + speed);
        System.out.println("Ran: " + ranDistance);
        System.out.println("Type: " + type);
        System.out.println("Height: " + obstacleHeight);
    }


    public void jump() throws JavascriptException {
        driver.findElement(By.className("offline")).sendKeys(SPACE);
    }

    public void dodge() throws JavascriptException {
        driver.findElement(By.className("offline")).sendKeys(ARROW_DOWN);
    }

    public Double getDistance() {
        try {
            Number firstObstacleXPos = getFirstObstacleXPos();
            return Double.valueOf(String.valueOf(firstObstacleXPos)) - trexXPos;
        } catch (JavascriptException e) {
            return -1.0;
        }
    }

    public Double getFirstObstacleType() {
        try {
            String response = (String) driver.executeScript("return Runner.instance_.horizon.obstacles[0].typeConfig.type");
            if ("CACTUS_LARGE".equals(response)) return 1.0;
            else if ("CACTUS_SMALL".equals(response)) return 2.0;
            else if ("PTERODACTYL".equals(response)) return 3.0;
            return -1.0;
        } catch (JavascriptException e) {
            return -1.0;
        }
    }

    public Number getFirstObstacleHeight() {
        try {
            return (Number) (driver.executeScript("return Runner.instance_.horizon.obstacles[0].typeConfig.height"));
        } catch (JavascriptException | NumberFormatException e) {
            return -1;
        }
    }

    public Number getFirstObstacleYPos() {
        try {
            return (Number) (driver.executeScript("return Runner.instance_.horizon.obstacles[0].yPos"));
        } catch (JavascriptException | NumberFormatException e) {
            return -1;
        }
    }

    public Number getFirstObstacleXPos() {
        try {
            return (Number) (driver.executeScript("return Runner.instance_.horizon.obstacles[0].xPos"));
        } catch (JavascriptException | NumberFormatException e) {
            return -1;
        }
    }

    public Number getTrexXPos() {
        try {
            return (Number) (driver.executeScript("return Runner.instance_.tRex.xPos"));
        } catch (JavascriptException e) {
            return -1;
        }
    }

    public Number getDistanceRan() {
        try {
            return (Number) (driver.executeScript("return Runner.instance_.distanceRan"));
        } catch (JavascriptException e) {
            return -1;
        }
    }

    public Number getCurrentSpeed() {
        try {
            return (Number) (driver.executeScript("return Runner.instance_.currentSpeed"));
        } catch (JavascriptException e) {
            return -1;
        }
    }

    public boolean getCrashed() {
        try {
            return (Boolean) driver.executeScript("return Runner.instance_.crashed");
        } catch (JavascriptException e) {
            return false;
        }
    }
}
