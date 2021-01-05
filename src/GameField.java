import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private Image dot;
    private Image secdots;
    private Image apple;
    private Image brake;
    private Image king;
    private int appleX;
    private int appleY;
    private int brakeX;
    private int brakeY;
    private int kingX;
    private int kingY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int[] secX = new int[ALL_DOTS];
    private int[] secY = new int[ALL_DOTS];
    private int[] kx = new int[ALL_DOTS];
    private int[] ky = new int[ALL_DOTS];
    private int dots;
    private int sdots;
    private int kings;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean sleft = false;
    private boolean sright = true;
    private boolean sup = false;
    private boolean sdown = false;
    private boolean kleft = false;
    private boolean kright = true;
    private boolean kup = false;
    private boolean kdown = false;
    private boolean inGame = true;
    private boolean chk = false;


    public GameField() {
        setBackground(Color.CYAN);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListenner());
        setFocusable(true);
        StartTimer();
        KeyBoardTimer();


    }

    public void initGame() {
        dots = 3;
        sdots = 3;
        kings = 1;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }
        for (int i = 0; i < kings; i++) {
            kx[i] = 120 - i * DOT_SIZE;
            ky[i] = 120;
        }
        kings = 1;
        for (int i = 0; i < sdots; i++) {
            secX[i] = 68 - i * DOT_SIZE;
            secY[i] = 68;
        }
    }

    public void StartTimer() {
        timer = new Timer(200, this);
        timer.start();
        createBrake();
        createApple();


    }

    public void createApple() {

        appleX = new Random().nextInt(15) * DOT_SIZE;
        appleY = new Random().nextInt(15) * DOT_SIZE;

    }

    public void createBrake() {
        brakeX = -100;
        brakeY = -1;
    }

    public void createKing() {
        //  kingX = new Random().nextInt(10) * DOT_SIZE;
        // kingY = new Random().nextInt(10) * DOT_SIZE;
    }

    public void loadImages() {
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();
        ImageIcon brak = new ImageIcon("brake.png");
        brake = brak.getImage();
        ImageIcon kin = new ImageIcon("king.png");
        //   king = kin.getImage();
        ImageIcon sec = new ImageIcon("secdots.png");
        secdots = sec.getImage();
    }

    public void deleteKing() {
//kingX = 0;
//kingY = 0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int score = dots - 3;
        g.drawImage(brake, brakeX, brakeY, this);
        if (inGame) {
            g.drawImage(king, kingX, kingY, this);
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);

            }
            for (int i = 0; i < sdots; i++) {
                g.drawImage(secdots, secX[i], secY[i], this);

            }
            for (int i = 0; i < kings; i++) {
                g.drawImage(king, kx[i], ky[i], this);

            }
            String str = "Your score - ";
            Font f = new Font("Arial", Font.BOLD, 14);
            g.setColor(Color.BLACK);
            g.setFont(f);
            g.drawString(str + String.valueOf(score), 115, 300);


        } else {
            String res = "press ENTER to restart";
            String str = "Game Over";
            String brl = "Your  score - ";
            Font f = new Font("Arial", Font.BOLD, 14);
            g.setColor(Color.BLACK);
            g.setFont(f);
            g.drawString(str, 125, SIZE / 2);
            g.drawString(brl + score, 125, 300);
            g.drawString(res, 20, 20);

        }

    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];

        }
        if (left) {
            x[0] -= DOT_SIZE;
        }
        if (right) {
            x[0] += DOT_SIZE;
        }
        if (up) {
            y[0] -= DOT_SIZE;
        }
        if (down) {
            y[0] += DOT_SIZE;
        }
    }

    public void secDOTmove() {
        for (int i = sdots; i > 0; i--) {
            secX[i] = secX[i - 1];
            secY[i] = secY[i - 1];

        }
        if (sleft) {
            secX[0] -= DOT_SIZE;
        }
        if (sright) {
            secX[0] += DOT_SIZE;
        }
        if (sup) {
            secY[0] -= DOT_SIZE;
        }
        if (sdown) {
            secY[0] += DOT_SIZE;
        }
    }

    public void moveKing() {
        for (int i = kings; i > 0; i--) {
            kx[i] = kx[i - 1];
            ky[i] = ky[i - 1];


        }

        if (kleft) {
            kx[0] -= DOT_SIZE;
        }
        if (kright) {
            kx[0] += DOT_SIZE;
        }
        if (kup) {
            ky[0] -= DOT_SIZE;
        }
        if (kdown) {
            ky[0] += DOT_SIZE;
        }


    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            createApple();
        }
        if (secX[0] == appleX && secY[0] == appleY) {
            sdots++;
            createApple();
        }
        for (int i = 1; i < x.length; i++) {
            if (x[i] == appleX && y[i] == appleY) {
                createApple();
            }
            for (int j = 1; j < secX.length; j++) {
                if (secX[i] == appleX && secY[i] == appleY) {
                    createApple();
                }
            }
        }
    }

    public void chekKing() {
        if (x[0] == kingX && y[0] == kingY) {
            inGame = false;
        }
        for (int i = 1; i < x.length; i++) {
            if (x[i] == kingX && y[i] == kingY) {
                createKing();
            }
            if (kx[i] == appleX && ky[i] == appleY) {
                createApple();
            }
        }

    }

    public void checkCollisionis() {
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
            }

        }
        if (x[0] >= SIZE) {
            inGame = false;
        }
        if (x[0] < 0) {
            inGame = false;
        }
        if (y[0] >= SIZE) {
            inGame = false;
        }
        if (y[0] < 0) {
            inGame = false;
        }
        for (int i = sdots; i > 0; i--) {
            if (i > 4 && secX[0] == secX[i] && secY[0] == secY[i]) {
                inGame = false;
            }

        }
        if (secX[0] >= SIZE) {
            inGame = false;
        }
        if (secX[0] < 0) {
            inGame = false;
        }
        if (secY[0] >= SIZE) {
            inGame = false;
        }
        if (secY[0] < 0) {
            inGame = false;
        }
        for (int i = kings; i > 0; i--) {
            if (i > 4 && kx[0] == kx[i] && ky[0] == ky[i]) {
                chk = false;
            }

        }
        if (kx[0] >= SIZE) {
            chk = false;
        }
        if (kx[0] < 0) {
            chk = false;
        }
        if (ky[0] >= SIZE) {
            chk = false;
        }
        if (ky[0] < 0) {
            chk = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            chekKing();
            checkCollisionis();
            move();
            secDOTmove();
            moveKing();
        }
        repaint();
    }

    //@Override
   /* public void paintComponents(Graphics g) {
        super.paintComponents(g);
        int score = dots;
        // Font f = new Font("Arial",14,Font.BOLD);
        g.setColor(Color.WHITE);
        // g.setFont(f);
        g.drawString(String.valueOf(score),125,SIZE/2);

    } */
    public void KeyBoardTimer() {
        timer = new Timer(250, this);
        timer.start();
    }
    //public void StartKingTimer(){
    //timer = new Timer(400,this);
    //timer.start();
    // createKing();


    //}
    class FieldKeyListenner extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down) {
                right = false;
                up = true;
                left = false;
            }
            if (key == KeyEvent.VK_DOWN && !up) {
                right = false;
                down = true;
                left = false;
            }
            if (key == KeyEvent.VK_ENTER && inGame == false) {
                inGame = true;
                initGame();


            }
            if (key == KeyEvent.VK_CONTROL && inGame == true) {
                dots++;


            }
            if (key == KeyEvent.VK_SHIFT && inGame == true) {
                createApple();


            }
            if (key == KeyEvent.VK_A && !sright) {
                sleft = true;
                sup = false;
                sdown = false;
            }
            if (key == KeyEvent.VK_D && !sleft) {
                sright = true;
                sup = false;
                sdown = false;
            }
            if (key == KeyEvent.VK_W && !sdown) {
                sright = false;
                sup = true;
                sleft = false;
            }
            if (key == KeyEvent.VK_S && !sup) {
                sright = false;
                sdown = true;
                sleft = false;


            }
        }

        public void kingGO() {
            if (inGame) {
                deleteKing();

            }


        }
    }
}


