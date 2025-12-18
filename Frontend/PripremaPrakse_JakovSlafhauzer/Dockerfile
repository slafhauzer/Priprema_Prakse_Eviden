# 1) Build stage
FROM node:20 AS build
WORKDIR /app

COPY package*.json ./
RUN npm ci

COPY . .
RUN npm run build

# 2) Run stage (nginx)
FROM nginx:alpine
# promijeni "YOUR_DIST_FOLDER" u stvarno ime foldera unutar dist/
COPY --from=build /app/dist/PripremaPrakse_JakovSlafhauzer/browser/ /usr/share/nginx/html/


EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
