# Intellij SvirtualKit

An Intellij plugin to modify the SvelteKit project tree to make it easier to navigate between files.

## Features

### Route files are respresented by name

- +page.svelte is modified to {route}.svelte
- +page.server.js is modified to {route}.server.js
- +page.js is modified to {route}.js

Same for ts files

![routes.png](src/main/resources/images/routes.png)

### Nesting

+page.server.js and +page.js are nested under +page.svelte

![nesting.png](src/main/resources/images/nesting.png)

### Tabs

The tab titles are modified to show the route name

![tabs.png](src/main/resources/images/tabs.png)

### Goto file

The goto file dialog is modified to show the new file names

![goto.png](src/main/resources/images/goto-file.png)

## Todo

- [ ] Add a setting toggle nesting
- [ ] Handle layout files
- [ ] Handle routes with params
- [x] Extend file search