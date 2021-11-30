

https://stackoverflow.com/questions/31388787/doesnt-find-exist-on-any-kind-of-typescript-or-javascript-array

Doesn't “find” exist on any kind of typescript or javascript array?

Array.prototype.find() is part of ES6. Link => developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/

Since Typescript 2.0 you could also use the --lib compiler flag or a "lib": [] section in your tsconfig.js file to include ES6 features, while still targeting ES5. See https://github.com/Microsoft/TypeScript/issues/6974

In this case just include the following configuration options in your tsconfig.js:

...
"lib": [ "es6" ],
"target": "es5"

"lib": ["dom", "dom.iterable", "esnext"],