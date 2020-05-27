from gomoku import *
from NeuralActor import NeuralActor
import math

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
	agent_exps = [[], []]
	bet_exps = [[], []]
	prevState = 0
	width = 15
	rand_coef = 0.15

	for i in range(games):

		print("Playing game {}...".format(i+1))
		state, betState, available = makeGame()

		lastPlayer = -1
		c=0
		while True:
			print("Playing game {} Epoch {}".format(i+1, c+1))
			qvals1 = agents[0].bet(state, betState)
			qvals2 = agents[1].bet(state, betState)

			bet_qvals = [qvals1, qvals2]
			bets = [0, 0]
			if(random.random() < rand_coef):
				bets[0] = int(random.random()*betState[0])
				bets[1] = int(random.random()*betState[1])
			else:
				bets[0] = np.argmax(np.array(qvals1)[:betState[0]+1])
				bets[1] = np.argmax(np.array(qvals2)[:betState[1]+1])

			if(bets[0] > bets[1]):
				agent = agents[0]
				lastPlayer = 0
			else:
				agent = agents[1]
				lastPlayer = 1

			qval = agent.predict(state.reshape(1, 2 * width**2))

			if(random.random() < rand_coef):
				x = np.random.randint(available.shape[0])
				y = np.random.randint(available.shape[1])

				while available[x][y] != 0:
					x = np.random.randint(available.shape[0])
					y = np.random.randint(available.shape[1])
				action = (x, y)
			else:
				index = np.argmax(qval + available.reshape((1, width**2)))
				action = (index//available.shape[0], index%available.shape[0])

			new_state, bets, new_available = makeMove(state, available, action, bets[agent.id], agent)
			betState = list(np.asarray(betState) + np.asarray(bets))
			rewards = getReward(new_state, new_available, agent, won = winGame(new_state[:,:, agent.id]))
			q_value = computeQ(new_state, new_available, agent)
			labels = compute_label(q_value, qval, action, rewards, agent, winGame(new_state[:,:, agent.id]))

			bet_q = computeBetQ( new_state, betState, new_available, agent)
			bet_labels = compute_bet_label(bet_q, bet_qvals[agent.id], max(bets), agent, winGame(new_state[:,:, agent.id]))

			temp_exp, x_train, y_train = check_exp(agent_exps[agent.id], state.reshape(1, 2*width**2), labels[agent.id])
			agent_exps[agent.id] = temp_exp

			agent.fit(x_train, y_train, batchsize = 32)

			temp_exp, x_train, x_train_bet, y_train = check_exp_bet(bet_exps[agent.id], state.reshape(1, 2*width**2), list(np.asarray(betState) - np.asarray(bets)), bet_labels[agent.id])
			bet_exps[agent.id] = temp_exp

			agent.bet_fit(x_train, x_train_bet, y_train, batchsize = 32)


			if(not isinstance(labels[1-agent.id], bool)):
				print("""ENTERED WIN LOOP -------------------------------------------------------------------------------------------------
					-----------------------------------------------------------------------------------------------------------------------
					========================================================================================================================
					------------------------------------------------------------------------------------------------------------------------""")
				temp_exp, x_train, y_train = check_exp(agent_exps[1 - agent.id], prevState.reshape(1, 2*width**2), labels[1 - agent.id])
				agents[1-agent.id].fit(x_train, y_train, batchsize = 32)
				agent_exps[1 - agent.id] = temp_exp

				temp_exp, x_train, x_train_bet, y_train = check_exp_bet(bet_exps[1-agent.id], state.reshape(1, 2*width**2), list(np.asarray(betState) - np.asarray(bets)), bet_labels[1-agent.id])
				bet_exps[1-agent.id] = temp_exp

				agents[1-agent.id].bet_fit(x_train, x_train_bet, y_train, batchsize = 32)

			agent.setPrevQ(qval)
			agent.setPrevBetQ(bet_labels[agent.id])
			agent.setPrevAction(action)
			agent.setPrevBet(bets[agent.id])
			agent.getOpponent().setPrevBet(bets[1 - agent.id])
			prevState = state
			state, available = new_state, new_available
			rand_coef = rand_coef*0.995
			c += 1

			if(winGame(state[:,:,0]) or winGame(state[:,:,1]) or fullGrid(available) or abs(betState[0] - betState[1]) == 100):
				break

		



				# train
		if((i+1)%50 == 0):
			a1.model.save("gomoku_predictor_2.h5")
			a1.bet_model.save("gomoku_better_2.h5")

train(300)





				

			
