/* eslint-disable max-statements-per-line */
/* eslint-disable indent */
// lint-staged.config.cjs
const { ESLint } = require('eslint');
const eslint = new ESLint();

const asyncFilter = async (arr, predicate) =>
	arr.reduce(async (acc, v) => {
		const ok = await predicate(v);
		return [...(await acc), ...(ok ? [v] : [])];
	}, []);

const config = {
  'android/**/*.java': (filenames) =>
    `node ./build/scons gradlew checkJavaStyle --args --console plain -PchangedFiles='${filenames.join(',')}'`,

  'iphone/**/*.{m,h}': ['npx clang-format -style=file -i'],
  'iphone/TitaniumKit/TitaniumKit/Sources/API/TopTiModule.m': ['npm run ios-sanity-check --'],

  '*.js': async (files) => {
    const filtered = await asyncFilter(files, async (file) => {
      try { return !(await eslint.isPathIgnored(file)); } catch { return false; }
    });
    return filtered.length ? `eslint ${filtered.join(' ')}` : [];
  },
};

if (process.platform === 'darwin') {
  config['iphone/Classes/**/*.swift'] = ['swiftlint --fix'];
}

module.exports = config;
