import numpy as np
import random

width = 15

def makeGame(bet = 50, width = 15):
	gameState = np.zeros((width, width, 2))
	betState = np.array([bet, bet])
	availablePos = np.zeros((width, width))

	return gameState, betState, availablePos

def makeMove(gameState, availablePos, action, bet, agent):
	actor = agent
	available = np.zeros(availablePos.shape)
	available[:] = availablePos[:]
	betState = [0,0]
	if available[action] == 0:
		gameState[action][actor.id] = 1
		available[action] = float("-inf")
		betState[actor.id] -= bet
		betState[1 - actor.id] += bet
		return gameState, betState, available
	else:
		raise ValueError("Couldn't make move because {} not available for play".format(action))
	

def fullGrid(available):
	for i in range(available.shape[0]):
		for j in range(available.shape[1]):
			if available[(i, j)] == 0:
				return False
	return True

def winGame(actorState):
	shape = actorState.shape
	print(shape)
	b = False
	c=0
	maxc = 0
	for i in range(shape[0]):
		for j in range(shape[1]):
			if actorState[(i, j)] == 0:
				maxc = max(maxc, c)
				c = 0
			else:
				c += 1
	b = b or maxc >= 5
	c=0
	maxc = 0
	for j in range(shape[1]):
		for i in range(shape[0]):
			if actorState[(i, j)] == 0:
				maxc = max(maxc, c)
				c = 0
			else:
				c += 1
	b = b or maxc >= 5
	c=0
	maxc = 0
	for i in range(shape[0]):
		for j in range(shape[1]):
			for x in range(-5, 6):
				try:
					if actorState[(i+x, j+x)] == 0:
						maxc = max(maxc, c)
						c = 0
					else:
						c+=1
				except:
					continue
	b = b or maxc >= 5
	c=0
	maxc = 0
	for i in range(shape[0]):
		for j in range(shape[1]):
			for x in range(-5, 6):
				try:
					if actorState[(i+x, j-x)] == 0:
						maxc = max(maxc, c)
						c = 0
					else:
						c+=1
				except:
					continue
	b = b or maxc >= 5
	return b

def getReward(state, available, actor, win_reward=500, lose_reward=-1000,
			  even_reward=-100, keepgoing_reward=-10, bet_reward = -40, won = False):
	reward = [0,0]
	whose_turn = actor.id
	if won:
		reward[whose_turn] = win_reward
		reward[1 - whose_turn] = lose_reward

	elif fullGrid(available):
		reward = [even_reward, even_reward]

	else:
		reward[whose_turn] = keepgoing_reward

	return reward 

def computeQ( state, available, agent):
	qval = agent.predict(state.reshape(1, 2 * width**2))
	maxQval = max(max(qval + available.reshape((1, width**2))))
	return maxQval

def compute_label(maxQval, qvals, action, rewards, agent, won):
	labels = [False, False]
	print("Action is: ", action)
	index = action[0]*15 + action[1]
	if not won:
		newVals = qvals
		print("Shape of q-array is: ", newVals.shape)
		newVals[0][index] = rewards[1 - agent.id] + 0.2*maxQval
		labels[agent.id] = newVals
	else:
		newVals = qvals
		newVals[0][index] = rewards[agent.id]
		labels[agent.id] = newVals
		newVals = agent.getOpponent().getPrevQ()
		action = self.getPrevAction()
		index = action[0]*15 + action[1]
		newVals[0][index] = rewards[1 - agent.id]
		labels[1 - agent.id] = newVals

	return labels

def computeBetQ( state, money, available, agent):
	myQval = computeQ(state, available, agent)
	print("MyQval: ", myQval)
	opponentQval = computeQ(state, available, agent.getOpponent())
	print("Opponent Qval: ", opponentQval)
	qval = agent.getOpponent().predict(state.reshape(1, 2 * width**2))
	index = np.argmax( qval + available.reshape((1, width**2)))
	action = (index//available.shape[0], index%available.shape[0])
	temp_state, unnecessary, temp_available = makeMove(state, available, action, 0, agent)
	nextQval = computeQ(temp_state, temp_available, agent)
	print("Next Qval: ", nextQval)

	add_on = 0
	if(money[1 - agent.id] > 10*money[agent.id]):
		add_on -= 500
	elif(money[1 - agent.id] > 5*money[agent.id]):
		add_on -= 100
	elif(money[1 - agent.id] < 5*money[agent.id]):
		add_on += 100
	elif(money[1 - agent.id] < 10*money[agent.id]):
		add_on += 500

	return add_on + 0.75*myQval - 0.4*opponentQval + 0.1*nextQval

def compute_bet_label(label, qvals, bet, agent, won):
	labels = [False, False]
	print("Label is: ", label)
	print("Newest bug about betting:" , bet)
	if not won:
		newVals = qvals
		newVals[bet] = label
		labels[agent.id] = newVals
	else:
		newVals = qvals
		newVals[bet] = 1000
		labels[agent.id] = newVals
		newVals = agent.getOpponent().getPrevQ()
		if(newVals != -1):
			index = self.getPrevBet()
			newVals[index] = rewards[1 - agent.id]
			labels[1 - agent.id] = newVals

	return labels

def check_exp(experiences, state, label, batch = 32):
	exp = experiences
	if(len(experiences) < batch):
		array = exp
		array.append((state, label))
	else:
		array = random.sample(experiences, batch-1)
		array.append((state, label))
		if(len(exp) >= 200):
			exp.pop(0).append((state, label))
	return exp, np.asarray([i[0] for i in array]), np.asarray([i[1] for i in array])

def check_exp_bet(experiences, state, bets, label, batch = 32):
	exp = experiences
	bets = np.asarray(bets).reshape((1,2))
	if(len(experiences) < batch):
		array = exp
		array.append((state, bets, label))
	else:
		array = random.sample(experiences, batch-1)
		array.append((state, bets, label))
		if(len(exp) >= 200):
			exp.pop(0).append((state, bets, label))
	return exp, np.asarray([i[0] for i in array]), np.asarray([i[1] for i in array]), np.asarray([i[2] for i in array])








