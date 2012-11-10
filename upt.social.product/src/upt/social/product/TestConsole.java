package upt.social.product;

import java.util.Vector;

import upt.social.generation.SocialGenerator;
import upt.social.model.NodeCoordinate;
import upt.social.model.STATE;
import upt.social.model.SocialNetwork;
import upt.social.model.factory.AnalogModelFactory;
import upt.social.simulation.threading.TimerLayer;
import upt.social.state.SimConfig;
import upt.social.ui.middleware.ConsoleWatchdog;
import upt.social.ui.middleware.Watchdog;

public class TestConsole {
	private TimerLayer timerManager;
	private Watchdog dog;

	public TestConsole() {
		// network settings
		int N = 9900;
		int S = 2;
		int WIDTH = 20;
		int HEIGHT = 20;
		double density = 0.75;
		double proFactor = 0.5;
		boolean checkConnectedGraph = false;

		// threading settings
		int NumTimers = 4;
		int NumPoolThreads = 4;
		int baseTick = 50;
		SimConfig.MinSleep = 2;
		SimConfig.MaxSleep = 10;

		// simulation settings
		int monitorInterval = 500;
		int simulationLength = 100; // # of monitoring ticks (=20s)

		SocialGenerator generator = new SocialGenerator(
				new AnalogModelFactory());

		Vector<NodeCoordinate> agents = new Vector<NodeCoordinate>();
		agents.add(new NodeCoordinate(0, 0, STATE.YES));
		agents.add(new NodeCoordinate(WIDTH - 1, HEIGHT - 1, STATE.NO));

		SocialNetwork network = generator.createNewNetwork(WIDTH, HEIGHT,
				agents, new Vector<NodeCoordinate>(), "HV");

		timerManager = new TimerLayer(network, NumPoolThreads);

		// start watchdog
		dog = new ConsoleWatchdog(timerManager, network, monitorInterval, false);
		dog.start();

		// start simulation
		timerManager.start();

	}

	public static void main(String[] args) {
		new TestConsole();
	}
}
