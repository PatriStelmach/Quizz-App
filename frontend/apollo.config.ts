module.exports = {
  client: {
    service: {
      name: 'quiz-client',
      url: 'http://localhost:10000/graphql',
    },
    includes: [
      'src/**/*.vue',
      'src/**/*.ts',
    ],
  },
}
