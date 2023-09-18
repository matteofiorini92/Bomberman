package controller;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Direction;

public class LoadKeyListeners {
	
	public static LoadKeyListeners loadKeyListeners;
	
	private model.BomberMan modelBm = model.BomberMan.getInstance();
	private view.GameBody viewBoard = view.GameBody.getInstance();
	
	private long lastKeyPressTime = 0;
	private static Double throttleDelay = model.Character.INITIAL_TIME_FOR_MOVEMENT / model.BomberMan.getInstance().getSpeed();
	private boolean keyHeld = false;
	
	private LoadKeyListeners() {
//		keyHeld = false;
	}
	
	public static LoadKeyListeners getInstance() {
		if (loadKeyListeners == null) {
			loadKeyListeners = new LoadKeyListeners();
		}
		return loadKeyListeners;
	}
	
	public static void updateThrottleDelay() {
		throttleDelay = model.Character.INITIAL_TIME_FOR_MOVEMENT / model.BomberMan.getInstance().getSpeed();
	}

	public void handleKeyPressed(KeyEvent event) {
	    if (!keyHeld) {
	        keyHeld = true;
	        processKeyPress(event);
	    }

    }

	public void handleKeyReleased(KeyEvent event) {
	    keyHeld = false;
	}

	@SuppressWarnings("deprecation")
	private void processKeyPress(KeyEvent event) {
		
	    long currentTime = System.currentTimeMillis();

	    if (currentTime - lastKeyPressTime >= throttleDelay) {
	    	lastKeyPressTime = currentTime;

	    	if (event.getCode() == KeyCode.SPACE && modelBm.getBombs() > 0 && !(model.BoardGame.getInstance().getCell(model.BomberMan.getInstance().getPosition()) instanceof model.Bomb)) {
		    
	    		view.BaseGroup baseGroup = view.BaseGroup.getInstance();
		    	int currBombs = modelBm.getBombs();
		    	model.Bomb modelBomb = new model.Bomb(currBombs, modelBm.getPosition());
		    	view.Bomb viewBomb = new view.Bomb();
		    	modelBomb.addObserver(viewBomb);
		    	modelBm.decBombs();
		    	modelBm.setTempStorage(modelBomb);
		    	// get viewBm stackPane index in order to add the bomb behind it
		    	int viewBmStackPaneIndex = baseGroup.getChildren().indexOf(viewBoard.getGridPane());
		    	
		    	view.GameBody.getInstance().getItemsPane().getChildren().add(viewBmStackPaneIndex + 1, viewBomb);
		    	modelBomb.trigger();	        	
		    } else if (event.getCode() == KeyCode.P) {
		    	model.BoardGame.getInstance().print();
		    } else if (event.getCode() == KeyCode.F) {
		    	model.BoardGame.getInstance().printExt();
		    }
		    else {	        	
		        	Direction direction = null;
		        	try {	        	
		        		switch (event.getCode()) {
		        		case DOWN:
		        			direction = Direction.DOWN;
		        			break;
		        		case RIGHT:
		        			direction = Direction.RIGHT;
		        			break;
		        		case UP:
		        			direction = Direction.UP;
		        			break;
		        		case LEFT:
		        			direction = Direction.LEFT;
		        			break;
		        		default:
		        			break;	
		        		}
		        		modelBm.move(direction);	        		
		        	} catch (NullPointerException e) {
		        		System.out.println("Invalid command");
		        	}
	        }
	    }

	    if (keyHeld) {
	    	Platform.runLater(() -> processKeyPress(event));
	    }
	}

}
