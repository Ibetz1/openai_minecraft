# MineGPT 

## Overview
**MineGPT** is a proof-of-concept plugin that connects **OpenAI’s ChatGPT** directly into Minecraft using simple HTTP requests.  
It demonstrates how external AI services can be embedded into a game environment to create interactive, dynamic, and even autonomous gameplay experiences.

The goal of this project is to show that **anything reachable via HTTP can be brought into Minecraft**—including large language models.

---

## Features

### In-Game ChatGPT Interface
Players can talk to ChatGPT directly from the Minecraft chat.

**Usage:**
`AI - <your prompt here>`

> **Example:**
> ```AI - explain redstone like I’m new```\
> Will give the output the same as if you asked ChatGPT directly.

---

### AI-Driven Command Execution
ChatGPT can interpret certain prompts and execute in-game commands on the player’s behalf.

> **Example:**
> ```AI - take me home```\
> Will execute the `/home` command enabling in-game commands via an AI prompt.


The plugin handles the request and delivers the generated content back into the game environment.

---

## Why This Exists
This project is meant as:
- A **technical demonstration** of HTTP-based AI integration
- A **playground** for experimenting with AI-driven gameplay
- A **foundation** for more advanced AI-powered Minecraft mods

It’s not just about ChatGPT—it’s about showing how **external systems can seamlessly interact with Minecraft**.

---

## How It Works (High Level)
1. Player sends a chat message prefixed with `AI -`
2. The plugin intercepts the message
3. An HTTP request is sent to OpenAI’s API
4. The response is parsed
5. The plugin either:
   - Sends the response back to chat
   - Executes a Minecraft command
   - Triggers an in-game action (e.g. art generation)

---

## Future Ideas
- NPCs powered by ChatGPT
- AI-generated quests
- World modification via natural language
- Multiplayer shared AI context
- Server moderation or automation

---

## Disclaimer
This project is experimental and intended for educational and demonstration purposes.  
Use responsibly and be mindful of API costs, rate limits, and permissions.
