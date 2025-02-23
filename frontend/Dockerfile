# Build stage
FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Production stage
FROM nginx:alpine
COPY --from=build /app/dist/taskmanager/browser /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Add health check using exec form
HEALTHCHECK --interval=30s --timeout=3s \
    CMD ["wget", "--quiet", "--tries=1", "--spider", "http://localhost:80", "||", "exit", "1"]

# Use exec form for CMD (nginx image uses CMD instead of ENTRYPOINT)
CMD ["nginx", "-g", "daemon off;"]
