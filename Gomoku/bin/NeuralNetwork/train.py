\


def train(games):
	""" if game is won:
			give appropriate rewards
			break

		else:
			agent1 bets:
				85 percent of time predict, 15 percent random
			agent2 bets:
				85 percent of time predicts, 15 percent random
			check whose bet is bigger
			play "optimal" move
			get rewards
			compute q value for next state
			compute labels
			use experiences?
			fit models
					"""
	a1 = NeuralActor(0)
	a2 = NeuralActor(1)
	a1.setOpponent(a2)
	a2.setOpponent(a1)
	agents = [a1, a2]
	agent_exps = [[0], [0]]

	for i in range(games):

		print("Playing game {}...".format(i+1))
		state, betState, available = makeGame()

		lastPlayer = -1
		while True:

			qvals1 = agents[0].bet(state, betState)
			qvals2 = agents[1].bet(state, betState)
			bets = [0,0]
			if(random.random() < 0.1):
				actions[0] = random.random()*betState[0]
				actions[1] = random.random()*betState[1]
			else:
				actions[0] = np.argmax(np.array(qvals1)[:betState[0]+1])
				actions[1] = np.argmax(np.array(qvals2)[:betState[1]+1])

			if(actions[0] > actions[1]):
				agent = agents[0]
				lastPlayer = 0
			else:
				agent = agents[1]
				lastPlayer = 1

			qval = agent.predict(state.reshape(1, 2 * width**2))

            if random.random() < 0.1:
                x = np.random.randint(available.shape[0])
                y = np.random.randint(available.shape[1])

                while available[x][y] != 0:
                    x = np.random.randint(available.shape[0])
                	y = np.random.randint(available.shape[1])
                action = (x, y)
            else:
            	index = np.argmax(qval + available.reshape((1, width**2)))
            	action = (index//available[0], index%available[0])

            	new_state, bets, new_available = makeMove(state, available, action, bets[agent.id], agent)
            	




				

			
