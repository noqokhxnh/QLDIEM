package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * Modern button with hover effects and rounded corners
 */
public class ModernButton extends JButton {
    
    private Color backgroundColor;
    private Color hoverColor;
    private Color pressedColor;
    private boolean isSelected = false;
    private boolean isHovered = false;
    private boolean isPressed = false;
    
    public ModernButton(String text, Color backgroundColor) {
        super(text);
        this.backgroundColor = backgroundColor;
        this.hoverColor = brighter(backgroundColor, 0.1f);
        this.pressedColor = darker(backgroundColor, 0.1f);
        
        initButton();
        setupMouseListeners();
    }
    
    private void initButton() {
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setForeground(Color.WHITE);
        setBackground(backgroundColor);
        setBorder(null);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        setPreferredSize(new Dimension(240, 45));
        setMaximumSize(new Dimension(240, 45));
        setMinimumSize(new Dimension(240, 45));
        
        setHorizontalAlignment(SwingConstants.LEFT);
        setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
    }
    
    private void setupMouseListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                isPressed = true;
                repaint();
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                isPressed = false;
                repaint();
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Determine background color based on state
        Color bgColor = backgroundColor;
        if (isSelected) {
            bgColor = pressedColor;
        } else if (isPressed) {
            bgColor = pressedColor;
        } else if (isHovered) {
            bgColor = hoverColor;
        }
        
        // Draw rounded rectangle background
        g2d.setColor(bgColor);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        
        // Add subtle glow effect for selected state
        if (isSelected) {
            g2d.setColor(new Color(255, 255, 255, 30));
            g2d.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 9, 9);
        }
        
        g2d.dispose();
        
        // Draw text
        super.paintComponent(g);
    }
    
    public void setSelected(boolean selected) {
        this.isSelected = selected;
        repaint();
    }
    
    public boolean isSelected() {
        return isSelected;
    }
    
    private Color brighter(Color color, float factor) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        
        r = Math.min(255, (int) (r + (255 - r) * factor));
        g = Math.min(255, (int) (g + (255 - g) * factor));
        b = Math.min(255, (int) (b + (255 - b) * factor));
        
        return new Color(r, g, b);
    }
    
    private Color darker(Color color, float factor) {
        int r = (int) (color.getRed() * (1 - factor));
        int g = (int) (color.getGreen() * (1 - factor));
        int b = (int) (color.getBlue() * (1 - factor));
        
        return new Color(r, g, b);
    }
}