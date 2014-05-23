package cabage.model.logic;

import java.sql.SQLException;
import java.util.List;

import cabbage.beans.LeaderBoardBean;
import cabbage.controller.Cupcake;

public class LeaderBoardGamePLay {
	Cupcake cupcake = new Cupcake();
	public LeaderBoardGamePLay() {

	}

	public List<LeaderBoardBean> getLeaderBoardBeans() throws SQLException {
		cupcake = new Cupcake();
		List<LeaderBoardBean> l = cupcake.getAllPlayerCollectibles();
		return sortBeans(l);
	}

	private List<LeaderBoardBean> sortBeans(List<LeaderBoardBean> li) {

		if (li == null) return null;
		if (li.isEmpty()) return null;
		
		List<LeaderBoardBean> l = li;

		for (int x = 0; x < l.size() - 1; x ++){

			for (int i = 0; i < l.size() - 1; i ++){

				if (l.get(i).getPoints() > l.get(i+1).getPoints()) {
					LeaderBoardBean temp = l.get(i);
					l.set(i, l.get(i+1));
					l.set(i+1, temp);
				}

			}

		}

		return l;
	}

}
