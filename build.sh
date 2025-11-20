#!/bin/bash

echo "🔨 Building Games4Life Backend for Render..."
echo ""

# Step 1: Clean and compile
echo "📦 Step 1: Cleaning and building project..."
./mvnw clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "❌ Build failed!"
    exit 1
fi

echo "✅ Build successful!"
echo ""

# Step 2: Show JAR file
echo "📁 JAR file location:"
ls -lh target/games4life-backend-0.0.1-SNAPSHOT.jar

echo ""
echo "🚀 Ready to deploy on Render!"
echo ""
echo "Next steps:"
echo "1. Commit changes to git"
echo "2. Push to GitHub"
echo "3. Create Web Service on Render"
echo "4. Use start command: java -Dspring.profiles.active=prod -Dserver.port=\$PORT -jar target/games4life-backend-0.0.1-SNAPSHOT.jar"
