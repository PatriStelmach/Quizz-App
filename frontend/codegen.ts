
import type { CodegenConfig } from '@graphql-codegen/cli';

const config: CodegenConfig = {
  overwrite: true,
  schema: "http://localhost:10000/graphql",
  documents: "src/**/*.graphql",
  generates: {
    "src/generated/": {
      preset: "client",
      plugins: []
    }
  }
};

export default config;
