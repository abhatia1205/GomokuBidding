import numpy

def makeGame(bet = 50, width = 15):
	gameState = np.zeros((width, width, 2))
	betState = np.array([bet, bet])
	availablePos = np.zeros(width, width)

	return gameState, betState, availablePos

def makeMove(gameState, availablePos, action, bet, actor):
	available = np.zeros(availePos.shape)
	available[:] = availablePos[:]
	betState = [0,0]
	if available[action] = 0:
		state[action][actor.id] = 1
		available[action] = float("-inf")
		betState[actor.id] -= bet
		betState[1 - actor.id] += bet
		return state, betState, available
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

    elif fullGrid(state):
        reward = [even_reward, even_reward]

    else:
        reward[whose_turn] = keepgoing_reward

    return reward 
