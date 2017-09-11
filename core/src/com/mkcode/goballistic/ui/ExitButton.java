package com.mkcode.goballistic.ui;

import com.mkcode.goballistic.math.Vector2;
import com.mkcode.goballistic.util.Constants;

public class ExitButton extends AbstractRectUIElement {

	public ExitButton() {
		super(new Vector2(
						Constants.WND_WIDTH - Constants.EXIT_BUTTON_W - Constants.EXIT_BUTTON_MARGIN, 
						Constants.EXIT_BUTTON_MARGIN
				), 
				Constants.EXIT_BUTTON_W, 
				Constants.EXIT_BUTTON_W, 
				"door_out_scaled.png"
		);
	}
}
